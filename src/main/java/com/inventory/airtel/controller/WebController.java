package com.inventory.airtel.controller;

import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    private InventoryService inventoryService;

    // Login Page (The first thing users see at the URL)
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; 
    }

    // Handle Login Logic
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (inventoryService.authenticate(username, password)) {
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid Credentials for Rusizi Hub");
        return "login";
    }

    // Dashboard View (Manage Equipment Remotely)
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("assets", inventoryService.getAllAssets());
        model.addAttribute("contacts", inventoryService.getAllContacts());
        return "dashboard";
    }
}