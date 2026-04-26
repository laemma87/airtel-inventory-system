package com.inventory.airtel.controller;

import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collections;

@Controller
public class DashboardController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            var assets = inventoryService.getAllAssets();
            var contacts = inventoryService.getAllContacts();

            model.addAttribute("assets", assets != null ? assets : Collections.emptyList());
            model.addAttribute("assetCount", assets != null ? assets.size() : 0);
            model.addAttribute("staffCount", contacts != null ? contacts.size() : 0);
            
            return "dashboard"; 
        } catch (Exception e) {
            // Log the error and show an empty dashboard instead of crashing
            System.err.println("Dashboard Error: " + e.getMessage());
            model.addAttribute("assets", Collections.emptyList());
            model.addAttribute("assetCount", 0);
            model.addAttribute("staffCount", 0);
            model.addAttribute("dbError", "Database connection issue: " + e.getMessage());
            return "dashboard";
        }
    }
}