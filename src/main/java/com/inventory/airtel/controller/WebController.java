package com.inventory.airtel.controller;

import com.inventory.airtel.service.InventoryService;
import jakarta.servlet.http.HttpSession;
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

    // Login Page
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; 
    }

    // Handle Login Logic
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        HttpSession session, 
                        Model model) {
        
        if (inventoryService.authenticate(username, password)) {
            // SAVE USER TO SESSION
            session.setAttribute("user", username);
            return "redirect:/dashboard";
        }
        
        model.addAttribute("error", "Invalid Credentials for Rusizi Hub");
        return "login";
    }

    // Logout Logic
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // KILL THE SESSION
        session.invalidate();
        return "redirect:/?logout=true";
    }

    // Dashboard View with Security Check
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // SECURITY CHECK: If no user in session, send back to login
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }

        try {
            var assets = inventoryService.getAllAssets();
            var contacts = inventoryService.getAllContacts();

            model.addAttribute("assets", assets != null ? assets : Collections.emptyList());
            model.addAttribute("assetCount", assets != null ? assets.size() : 0);
            model.addAttribute("staffCount", contacts != null ? contacts.size() : 0);
            
        } catch (Exception e) {
            model.addAttribute("assets", Collections.emptyList());
            model.addAttribute("assetCount", 0);
            model.addAttribute("staffCount", 0);
        }
        return "dashboard";
    }
}