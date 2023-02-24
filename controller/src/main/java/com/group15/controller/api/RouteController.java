package com.group15.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

    String title = "EECS4413 Auction House";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index";
    }
}
