package com.tolls.smart_toll_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/admin")
    public String admin() { return "admin"; }


    @GetMapping("/")
    public String home() { return "redirect:/login"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String register() { return "register"; }

    @GetMapping("/dashboard")
    public String dashboard() { return "dashboard"; }

    @GetMapping("/vehicles")
    public String vehicles() { return "vehicles"; }

    @GetMapping("/wallet")
    public String wallet() { return "wallet"; }

    @GetMapping("/toll")
    public String toll() { return "toll"; }
}