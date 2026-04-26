package com.inventory.airtel.controller;

import com.inventory.airtel.model.Asset;
import com.inventory.airtel.model.Contact;
import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Collections;

@Controller
public class DashboardController {

    @Autowired
    private InventoryService inventoryService;

    // --- MAIN DASHBOARD ---
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
            model.addAttribute("assets", Collections.emptyList());
            model.addAttribute("assetCount", 0);
            model.addAttribute("staffCount", 0);
            return "dashboard";
        }
    }

    // --- ASSET REGISTRATION ---
    @GetMapping("/assets/new")
    public String showRegisterPage(Model model) {
        model.addAttribute("asset", new Asset()); // Provides empty object for form
        return "register_asset"; 
    }

    @PostMapping("/assets/save")
    public String saveAsset(@ModelAttribute Asset asset) {
        inventoryService.saveAsset(asset);
        return "redirect:/dashboard"; // Go back to see the new asset in the list
    }

    // --- STAFF CONTACTS ---
    @GetMapping("/contacts")
    public String showContacts(Model model) {
        var contacts = inventoryService.getAllContacts();
        model.addAttribute("contacts", contacts != null ? contacts : Collections.emptyList());
        return "contacts"; 
    }
}