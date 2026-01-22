package com.assessment.device_api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/swagger-test")
    public String swaggerTestEndpoint() {
        return "Swagger works!";
    }
}
