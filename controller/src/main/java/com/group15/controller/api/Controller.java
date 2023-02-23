package com.group15.controller.api;

import com.group15.controller.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final ControllerService controllerService;

    @Autowired
    public Controller(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    @GetMapping("home")
    public String test() {
        return "This is the homepage!";
    }

}
