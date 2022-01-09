package test.suman.StockBrokerage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableJpaRepositories
@EnableAsync
@SpringBootApplication
public class StockBrokerageApp {
    public static void main(String[] args) {
        SpringApplication.run(StockBrokerageApp.class, args);
    }
}