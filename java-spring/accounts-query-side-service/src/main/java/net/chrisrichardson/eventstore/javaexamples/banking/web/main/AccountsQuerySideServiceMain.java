package net.chrisrichardson.eventstore.javaexamples.banking.web.main;

import net.chrisrichardson.eventstore.javaexamples.banking.web.AccountsQuerySideServiceConfiguration;
import org.springframework.boot.SpringApplication;

public class AccountsQuerySideServiceMain {

  public static void main(String[] args) {
    String useEureka = System.getenv().get("USE_EUREKA");

    SpringApplication app = new SpringApplication(AccountsQuerySideServiceConfiguration.class);

    if(useEureka!=null && useEureka.equals("true")) {
      app.setAdditionalProfiles("enableEureka");
    }

    app.run(args);
  }
}
