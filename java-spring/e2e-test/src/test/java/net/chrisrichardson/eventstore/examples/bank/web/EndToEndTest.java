package net.chrisrichardson.eventstore.examples.bank.web;


import com.netflix.appinfo.CloudInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import net.chrisrichardson.eventstore.javaexamples.banking.common.customers.CustomerInfo;
import net.chrisrichardson.eventstore.javaexamples.banking.common.customers.CustomerResponse;
import net.chrisrichardson.eventstore.javaexamples.banking.commonauth.utils.BasicAuthUtils;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.accounts.CreateAccountRequest;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.accounts.CreateAccountResponse;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.transactions.CreateMoneyTransferRequest;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.transactions.CreateMoneyTransferResponse;
import net.chrisrichardson.eventstore.javaexamples.banking.web.queryside.accounts.GetAccountResponse;
import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping;
import net.chrisrichardson.eventstorestore.javaexamples.testutil.Producer;
import net.chrisrichardson.eventstorestore.javaexamples.testutil.Verifier;
import net.chrisrichardson.eventstorestore.javaexamples.testutil.customers.CustomersTestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static net.chrisrichardson.eventstorestore.javaexamples.testutil.TestUtil.eventually;
import static net.chrisrichardson.eventstorestore.javaexamples.testutil.customers.CustomersTestUtils.generateCustomerInfo;

public class EndToEndTest {

  private String getenv(String name, String defaultValue) {
    String x = System.getenv(name);
    return x == null ? defaultValue : x;
  }

  private String makeBaseUrl(String path) {
    return "http://" + getenv("SERVICE_HOST", "localhost") + ":" + 8080 + "/" + path;
  }

  RestTemplate restTemplate = new RestTemplate();

  CustomersTestUtils customersTestUtils;

  {

  for (HttpMessageConverter<?> mc : restTemplate.getMessageConverters()) {
    if (mc instanceof MappingJackson2HttpMessageConverter) {
      ((MappingJackson2HttpMessageConverter) mc).setObjectMapper(EventStoreCommonObjectMapping.getObjectMapper());
    }
  }

    customersTestUtils = new CustomersTestUtils(restTemplate, makeBaseUrl("/customers/"));
  }

  @Before
  public void init() {
    DiscoveryManager discoveryManager = DiscoveryManager.getInstance();
    discoveryManager.initComponent(
            new CloudInstanceConfig(),
            new DefaultEurekaClientConfig());

    awaitServiceInEureka("accounts-command-side-service", discoveryManager);
    awaitServiceInEureka("accounts-query-side-service", discoveryManager);
    awaitServiceInEureka("customers-command-side-service", discoveryManager);
    awaitServiceInEureka("customers-query-side-service", discoveryManager);
    awaitServiceInEureka("transactions-command-side-service", discoveryManager);
  }


  @Test
  public void shouldCreateCustomerAndAccountsAndTransferMoney() {
    CustomerInfo customerInfo = generateCustomerInfo();

    BigDecimal initialFromAccountBalance = new BigDecimal(500);
    BigDecimal initialToAccountBalance = new BigDecimal(100);
    BigDecimal amountToTransfer = new BigDecimal(150);

    BigDecimal finalFromAccountBalance = initialFromAccountBalance.subtract(amountToTransfer);
    BigDecimal finalToAccountBalance = initialToAccountBalance.add(amountToTransfer);

    final CustomerResponse customerResponse = restTemplate.postForEntity(makeBaseUrl("/customers"),customerInfo, CustomerResponse.class).getBody();
    final String customerId = customerResponse.getId();

    customersTestUtils.assertCustomerResponse(customerId, customerInfo);


    final CreateAccountResponse fromAccount = BasicAuthUtils.doBasicAuthenticatedRequest(restTemplate,
            makeBaseUrl("/accounts"),
            HttpMethod.POST,
            CreateAccountResponse.class,
            new CreateAccountRequest(customerId, "My #1 Account", initialFromAccountBalance)
    );
    final String fromAccountId = fromAccount.getAccountId();

    CreateAccountResponse toAccount = BasicAuthUtils.doBasicAuthenticatedRequest(restTemplate,
            makeBaseUrl("/accounts"),
            HttpMethod.POST,
            CreateAccountResponse.class,
            new CreateAccountRequest(customerId, "My #2 Account", initialToAccountBalance)
    );

    String toAccountId = toAccount.getAccountId();

    Assert.assertNotNull(fromAccountId);
    Assert.assertNotNull(toAccountId);

    assertAccountBalance(fromAccountId, initialFromAccountBalance);
    assertAccountBalance(toAccountId, initialToAccountBalance);


    final CreateMoneyTransferResponse moneyTransfer =  BasicAuthUtils.doBasicAuthenticatedRequest(restTemplate,
            makeBaseUrl("/transfers"),
            HttpMethod.POST,
            CreateMoneyTransferResponse.class,
            new CreateMoneyTransferRequest(fromAccountId, toAccountId, amountToTransfer)
    );

    assertAccountBalance(fromAccountId, finalFromAccountBalance);
    assertAccountBalance(toAccountId, finalToAccountBalance);

    // TOOD - check state of money transfer
  }

  private BigDecimal toCents(BigDecimal dollarAmount) {
    return dollarAmount.multiply(new BigDecimal(100));
  }

  private void assertAccountBalance(final String fromAccountId, final BigDecimal expectedBalanceInDollars) {
    final BigDecimal inCents = toCents(expectedBalanceInDollars);
    eventually(
            new Producer<GetAccountResponse>() {
              @Override
              public Observable<GetAccountResponse> produce() {
                  return Observable.just(BasicAuthUtils.doBasicAuthenticatedRequest(restTemplate,
                          makeBaseUrl("/accounts/" + fromAccountId),
                          HttpMethod.GET,
                          GetAccountResponse.class));
              }
            },
            new Verifier<GetAccountResponse>() {
              @Override
              public void verify(GetAccountResponse accountInfo) {
                Assert.assertEquals(fromAccountId, accountInfo.getAccountId());
                Assert.assertEquals(inCents, accountInfo.getBalance());
              }
            });
  }

  private void awaitServiceInEureka(String serviceName, DiscoveryManager discoveryManager) {
    try {
       Observable.interval(500, TimeUnit.MILLISECONDS)
              .take(100)
              .map(x -> discoveryManager.getLookupService().getInstancesById(serviceName))
              .filter(itemsList -> !itemsList.isEmpty())
              .toBlocking().first();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
