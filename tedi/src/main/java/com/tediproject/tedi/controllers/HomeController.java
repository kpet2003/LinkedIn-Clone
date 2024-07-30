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
    public String sign_up() {
        // Forward to index.html
        return "forward:/index.html";
    }

    @RequestMapping(value = "/HomePage")
    public String HomePage() {
        // Forward to index.html
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Network")
    public String Network() {
        // Forward to index.html
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Jobs")
    public String Jobs() {
        // Forward to index.html
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Messages")
    public String Messages() {
        // Forward to index.html
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Notifications")
    public String Notifications() {
        // Forward to index.html
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Profile")
    public String Profile() {
        // Forward to index.html
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Settings")
    public String Settings() {
        // Forward to index.html
        return "forward:/index.html";
    }
    


}

