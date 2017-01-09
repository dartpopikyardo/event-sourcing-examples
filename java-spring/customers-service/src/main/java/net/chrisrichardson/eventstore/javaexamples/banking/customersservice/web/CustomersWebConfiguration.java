package net.chrisrichardson.eventstore.javaexamples.banking.customersservice.web;

import net.chrisrichardson.eventstore.javaexamples.banking.customersservice.backend.CustomerBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({CustomerBackendConfiguration.class})
@ComponentScan
public class CustomersWebConfiguration extends WebMvcConfigurerAdapter {

}