package net.chrisrichardson.eventstore.javaexamples.banking.apigateway;

import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.filters.noneureka.ServiceByVerbFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by popikyardo on 25.03.16.
 */
@Configuration
@Profile({"disableEureka"})
public class NonEurekaClientConfiguration {
    @Bean
    public ServiceByVerbFilter getAccountsQuerySideFilter() {
        return new ServiceByVerbFilter();
    }
}
