package com.inventory.airtel.controller;

import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch data exactly like your MainFrame.refreshData() does
        var assets = inventoryService.getAllAssets();
        var contacts = inventoryService.getAllContacts();

        // Send the data to the HTML page
        model.addAttribute("assets", assets);
        model.addAttribute("assetCount", assets.size());
        model.addAttribute("staffCount", contacts.size());

        return "dashboard"; // This opens dashboard.html
    }
}