package com.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/route1")
    public String route1() {
        return "Change Menu:This is protected route1";
    }

    @GetMapping("/route2")
    public String route2() {
        return "Change Price: This is protected route2";
    }

    @GetMapping("/route3")
    public String route3() {
        return "Order: This is protected route3";
    }

    @GetMapping("/route4")
    public String route4() {
        return "Pay Bill: This is protected route4";
    }

    @GetMapping("/route5")
    public String route5() {
        return "Drinking Water: This is protected route5";
    }
}
