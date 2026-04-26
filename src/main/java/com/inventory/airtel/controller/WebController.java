package com.inventory.airtel.controller;

import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;

@Controller
public class WebController {

    @Autowired
    private InventoryService inventoryService;

    // Login Page (The first thing users see)
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; 
    }

    // Handle Login Logic
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        Model model) {
        
        if (inventoryService.authenticate(username, password)) {
            return "redirect:/dashboard";
        }
        
        model.addAttribute("error", "Invalid Credentials for Rusizi Hub");
        return "login";
    }

    // Dashboard View (Manage Equipment Remotely)
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            var assets = inventoryService.getAllAssets();
            var contacts = inventoryService.getAllContacts();

            model.addAttribute("assets", assets != null ? assets : Collections.emptyList());
            model.addAttribute("contacts", contacts != null ? contacts : Collections.emptyList());
            
            // Adding counts for the "Stat Cards" in your dashboard.html
            model.addAttribute("assetCount", assets != null ? assets.size() : 0);
            model.addAttribute("staffCount", contacts != null ? contacts.size() : 0);
            
        } catch (Exception e) {
            model.addAttribute("assets", Collections.emptyList());
            model.addAttribute("contacts", Collections.emptyList());
            model.addAttribute("assetCount", 0);
            model.addAttribute("staffCount", 0);
        }
        return "dashboard";
    }
}