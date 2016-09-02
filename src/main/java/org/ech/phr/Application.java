package org.ech.phr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
        
        //System.setProperty("hadoop.home.dir", "C:\\...\\phr_poc\\winutils");
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
    }

}
