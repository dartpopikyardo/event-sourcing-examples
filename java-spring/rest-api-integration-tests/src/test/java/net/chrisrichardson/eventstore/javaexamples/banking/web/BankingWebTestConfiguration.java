package net.chrisrichardson.eventstore.javaexamples.banking.web;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import io.eventuate.javaclient.spring.jdbc.EventuateJdbcEventStoreConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.commonauth.AuthConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.commonswagger.CommonSwaggerConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.accounts.CommandSideWebAccountsConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.customers.CustomersCommandSideWebConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.transactions.CommandSideWebTransactionsConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.customers.queryside.CustomersQuerySideWebConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.queryside.QuerySideWebConfiguration;
import net.chrisrichardson.eventstorestore.javaexamples.testutil.RestTemplateErrorHandler;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

@Configuration
@Import({CommandSideWebAccountsConfiguration.class, CommandSideWebTransactionsConfiguration.class, EmbeddedTestAggregateStoreConfiguration.class, QuerySideWebConfiguration.class, CustomersQuerySideWebConfiguration.class, CustomersCommandSideWebConfiguration.class, AuthConfiguration.class, CommonSwaggerConfiguration.class})
@EnableAutoConfiguration
public class BankingWebTestConfiguration extends WebMvcConfigurerAdapter {

  @Bean
  public HttpMessageConverters customConverters() {
    HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
    return new HttpMessageConverters(additional);
  }

  @Bean
  public RestTemplate restTemplate(HttpMessageConverters converters) {
    RestTemplate restTemplate = new RestTemplate();
    HttpMessageConverter<?> httpMessageConverter = converters.getConverters().get(0);
    List<? extends HttpMessageConverter<?>> httpMessageConverters = Arrays.asList(new MappingJackson2HttpMessageConverter());
    restTemplate.setMessageConverters((List<HttpMessageConverter<?>>) httpMessageConverters);

    restTemplate.setErrorHandler(new RestTemplateErrorHandler());
    return restTemplate;
  }
}
