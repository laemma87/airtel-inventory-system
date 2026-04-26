package com.inventory.airtel.controller;

import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private InventoryService inventoryService;

    // This shows the login page when you first open the site
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; 
    }

    // This handles the data when you click the "Login" button
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, 
                              @RequestParam String password, 
                              Model model) {
        
        if (inventoryService.authenticate(username, password)) {
            // Success! Send them to the dashboard
            return "redirect:/dashboard"; 
        } else {
            // Failure! Send them back to login with an error message
            model.addAttribute("error", "Invalid Credentials for Rusizi Hub");
            return "login";
        }
    }

    // Optional: Handle Logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}