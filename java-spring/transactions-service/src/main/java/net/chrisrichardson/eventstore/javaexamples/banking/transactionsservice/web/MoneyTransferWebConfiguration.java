package net.chrisrichardson.eventstore.javaexamples.banking.transactionsservice.web;

import net.chrisrichardson.eventstore.javaexamples.banking.transactionsservice.backend.MoneyTransferBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MoneyTransferBackendConfiguration.class})
@ComponentScan
public class MoneyTransferWebConfiguration {

}
