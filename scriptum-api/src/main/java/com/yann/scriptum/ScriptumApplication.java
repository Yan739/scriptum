package com.yann.scriptum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ScriptumApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScriptumApplication.class, args);
    }

}
