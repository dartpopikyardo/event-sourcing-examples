package net.chrisrichardson.eventstore.javaexamples.banking.customersviewservice.web;

import net.chrisrichardson.eventstore.javaexamples.banking.customersviewservice.backend.CustomerViewBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({CustomerViewBackendConfiguration.class})
@ComponentScan
public class CustomersViewWebConfiguration extends WebMvcConfigurerAdapter {
}
