package com.inventory.airtel.controller;

import com.inventory.airtel.model.Asset;
import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AssetController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/assets/new")
    public String showRegistrationForm(Model model) {
        model.addAttribute("asset", new Asset());
        return "register_asset";
    }

    @PostMapping("/assets/save")
    public String saveAsset(@ModelAttribute Asset asset) {
        try {
            // FIX: Check for BOTH null and 0 to ensure it triggers an INSERT
            if (asset.getId() == null || asset.getId() == 0) {
                // Set defaults for new assets
                asset.setStatus("In Store");
                asset.setAssignedTo("Unassigned");
                inventoryService.saveAsset(asset);
                System.out.println("Successfully INSERTED new asset: " + asset.getTagId());
            } else {
                inventoryService.updateAssetDetails(asset);
                System.out.println("Successfully UPDATED asset ID: " + asset.getId());
            }
            return "redirect:/dashboard";
            
        } catch (Exception e) {
            // This will print the EXACT database error in your Railway "Deploy Logs"
            System.err.println("CRITICAL ERROR DURING SAVE: " + e.getMessage());
            e.printStackTrace(); 
            return "redirect:/assets/new?error";
        }
    }
}