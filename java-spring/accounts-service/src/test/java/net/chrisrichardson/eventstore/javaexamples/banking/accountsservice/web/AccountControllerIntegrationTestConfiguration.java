package net.chrisrichardson.eventstore.javaexamples.banking.accountsservice.web;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AccountsWebConfiguration.class, EmbeddedTestAggregateStoreConfiguration.class})
public class AccountControllerIntegrationTestConfiguration {

}
