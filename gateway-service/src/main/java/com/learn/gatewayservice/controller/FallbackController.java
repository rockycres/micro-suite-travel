package com.learn.gatewayservice.controller;

import com.learn.gatewayservice.model.FallbackResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/fbService")
    public FallbackResponse getFallBackBackendA() {
        FallbackResponse a = new FallbackResponse();
        a.setMsgCode(500);
        a.setMsg("Please try again after sometime");
        return a;
    }



}