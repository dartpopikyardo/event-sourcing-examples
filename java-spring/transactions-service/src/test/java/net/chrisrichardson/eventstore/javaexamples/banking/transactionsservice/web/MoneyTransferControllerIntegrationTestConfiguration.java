package net.chrisrichardson.eventstore.javaexamples.banking.transactionsservice.web;

import io.eventuate.javaclient.spring.jdbc.EventuateJdbcEventStoreConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MoneyTransferWebConfiguration.class, EventuateJdbcEventStoreConfiguration.class})
@EnableAutoConfiguration
public class MoneyTransferControllerIntegrationTestConfiguration {
}
