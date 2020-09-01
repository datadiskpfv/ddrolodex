package uk.co.datadisk.ddrolodex.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class bootstrap implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading data via bootstrap");
        System.out.println("==========================");
    }
}
