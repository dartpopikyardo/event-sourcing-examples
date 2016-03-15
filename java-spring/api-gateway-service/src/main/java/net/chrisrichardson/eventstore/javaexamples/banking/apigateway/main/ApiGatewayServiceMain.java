package net.chrisrichardson.eventstore.javaexamples.banking.apigateway.main;

import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.ApiGatewayProperties;
import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.EurekaClientConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.filters.ServiceByVerbFilter;
import net.chrisrichardson.eventstore.javaexamples.banking.commonauth.AuthConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Created by Main on 19.01.2016.
 */
@SpringBootApplication
@EnableZuulProxy
@Import({AuthConfiguration.class, EurekaClientConfiguration.class})
@EnableConfigurationProperties({ApiGatewayProperties.class})
public class ApiGatewayServiceMain {
    @Bean
    public ServiceByVerbFilter getAccountsQuerySideFilter() {
        return new ServiceByVerbFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayServiceMain.class, args);
    }
}
