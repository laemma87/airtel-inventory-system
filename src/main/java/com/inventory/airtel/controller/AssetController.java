package com.inventory.airtel.controller;

import com.inventory.airtel.model.Asset;
import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AssetController {

    @Autowired
    private InventoryService inventoryService;

    // 1. Show the Registration Form
    @GetMapping("/assets/new")
    public String showRegistrationForm() {
        return "register_asset"; // This must match register_asset.html
    }

    // 2. Handle the Form Submission
    @PostMapping("/assets/save")
    public String saveAsset(@ModelAttribute Asset asset) {
        inventoryService.saveAsset(asset);
        return "redirect:/dashboard"; // Go back to dashboard after saving
    }
}