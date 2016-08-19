package org.ech.phr.rest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class DummyServiceImpl {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
