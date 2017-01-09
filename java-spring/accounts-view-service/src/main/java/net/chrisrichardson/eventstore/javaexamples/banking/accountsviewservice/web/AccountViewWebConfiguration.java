package net.chrisrichardson.eventstore.javaexamples.banking.accountsviewservice.web;

import net.chrisrichardson.eventstore.javaexamples.banking.accountsviewservice.backend.AccountViewBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AccountViewBackendConfiguration.class})
@ComponentScan
public class AccountViewWebConfiguration {


}
