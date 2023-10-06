package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;

@Controller
@RequestMapping()
public class WelcomePageController {

    @GetMapping()
    public String welcomePage(){
        return "welcomePage";
    }
}
