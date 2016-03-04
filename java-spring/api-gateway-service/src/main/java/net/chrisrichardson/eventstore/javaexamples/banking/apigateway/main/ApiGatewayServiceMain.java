package net.chrisrichardson.eventstore.javaexamples.banking.apigateway.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Main on 19.01.2016.
 */
@SpringBootApplication
@EnableZuulProxy
public class ApiGatewayServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayServiceMain.class, args);
    }
}
