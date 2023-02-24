package com.group15.controller.api;

import com.group15.controller.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class ProxyController {

    private final ControllerService controllerService;

    @Autowired
    public ProxyController(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

}
