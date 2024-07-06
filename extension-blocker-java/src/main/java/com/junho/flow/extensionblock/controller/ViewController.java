package com.junho.flow.extensionblock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String mainPage() {
        return "file-extension-block";
    }

}
