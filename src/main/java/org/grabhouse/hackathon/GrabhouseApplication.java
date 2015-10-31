package org.grabhouse.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GrabhouseApplication {

    public static void main(String[] args) {
    	ConfigurableApplicationContext ctx = SpringApplication.run(Configuration.class, args);
//    	KettleEnvironment.init();
    }
}
