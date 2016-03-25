package net.chrisrichardson.eventstore.javaexamples.banking.apigateway;

import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.filters.eureka.ServiceByVerbFilter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by popikyardo on 15.03.16.
 */
@Configuration
@EnableEurekaClient
@Profile({"enableEureka"})
public class EurekaClientConfiguration {
    @Bean
    public ServiceByVerbFilter getAccountsQuerySideFilter() {
        return new ServiceByVerbFilter();
    }
}