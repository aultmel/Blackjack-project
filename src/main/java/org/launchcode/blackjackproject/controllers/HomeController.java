package org.launchcode.blackjackproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home() {
        return "index";
    }

    @GetMapping("rules")
    public String rulesPage(Model model) {
        model.addAttribute("title", "Rules");
        return "rules";
    }

}
