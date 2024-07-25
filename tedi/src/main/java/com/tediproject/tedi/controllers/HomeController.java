package com.tediproject.tedi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String redirect() {
        // Forward to index.html
        return "forward:/index.html";
    }
    @RequestMapping(value = "/SignUp")
    public String SignUp() {
        // Forward to index.html
        return "forward:/index.html";
    }
}

