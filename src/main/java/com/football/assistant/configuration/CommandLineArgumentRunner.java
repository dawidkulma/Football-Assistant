package com.football.assistant.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineArgumentRunner implements CommandLineRunner {

    /*This class can access comand line arguments and run arbitrary code on them*/
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Command Line Runner init...");
    }
}
