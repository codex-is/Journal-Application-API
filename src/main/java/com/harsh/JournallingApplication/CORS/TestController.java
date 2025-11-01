package com.harsh.JournallingApplication.CORS;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "CORS working globally!";
    }
}
