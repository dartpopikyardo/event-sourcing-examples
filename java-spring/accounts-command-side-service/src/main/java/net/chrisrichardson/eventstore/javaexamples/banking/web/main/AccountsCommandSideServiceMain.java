package net.chrisrichardson.eventstore.javaexamples.banking.web.main;

import net.chrisrichardson.eventstore.javaexamples.banking.web.AccountsCommandSideServiceConfiguration;
import org.springframework.boot.SpringApplication;

public class AccountsCommandSideServiceMain {

  public static void main(String[] args) {
    String useEureka = System.getenv().get("USE_EUREKA");

    SpringApplication app = new SpringApplication(AccountsCommandSideServiceConfiguration.class);

    if(useEureka!=null && useEureka.equals("true")) {
      app.setAdditionalProfiles("enableEureka");
    }

    app.run(args);
  }
}
