package net.chrisrichardson.eventstore.javaexamples.banking.apigateway.main;

import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.ApiGatewayProperties;
import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.filters.ServiceByVerbFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by Main on 19.01.2016.
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
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
