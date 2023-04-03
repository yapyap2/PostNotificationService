package com.yapyap.postserviceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("/applicationContext.xml")
public class PostServiceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostServiceProjectApplication.class, args);
    }

}
