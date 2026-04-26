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
        // We take the logic from the Swing file and put it here
        if (asset.getId() == null) {
            asset.setStatus("In Store");
            asset.setAssignedTo("Unassigned");
            inventoryService.saveAsset(asset);
        } else {
            inventoryService.updateAssetDetails(asset);
        }
        return "redirect:/dashboard";
    }
}