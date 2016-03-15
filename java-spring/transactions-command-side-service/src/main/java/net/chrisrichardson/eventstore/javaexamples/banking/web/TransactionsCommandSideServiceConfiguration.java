package net.chrisrichardson.eventstore.javaexamples.banking.web;

import net.chrisrichardson.eventstore.client.config.EventStoreHttpClientConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.commonswagger.CommonSwaggerConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.transactions.CommandSideWebTransactionsConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@Import({CommandSideWebTransactionsConfiguration.class, EventStoreHttpClientConfiguration.class, CommonSwaggerConfiguration.class, EurekaClientConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
public class TransactionsCommandSideServiceConfiguration {


  @Bean
  public HttpMessageConverters customConverters() {
    HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
    return new HttpMessageConverters(additional);
  }

}
