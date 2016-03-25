package net.chrisrichardson.eventstore.javaexamples.banking.apigateway;

import net.chrisrichardson.eventstore.javaexamples.banking.commonauth.AuthConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

/**
 * Created by Main on 19.01.2016.
 */
@SpringBootApplication
@EnableZuulProxy
@Import({AuthConfiguration.class})
@EnableConfigurationProperties({ApiGatewayProperties.class})
public class ApiGatewayServiceMain {

    public static void main(String[] args) {
        String useEureka = System.getenv().get("USE_EUREKA");

        SpringApplication app = new SpringApplication(ApiGatewayServiceMain.class);

        if(useEureka!=null && useEureka.equals("true")) {
            app.setAdditionalProfiles("enableEureka");
        } else {
            app.setAdditionalProfiles("disableEureka");
        }

        app.run(args);
    }
}
