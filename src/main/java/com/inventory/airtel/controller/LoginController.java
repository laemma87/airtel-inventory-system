package com.inventory.airtel.controller;

import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login"; 
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, 
                             @RequestParam String password, 
                             Model model) {
        
        if (inventoryService.authenticate(username, password)) {
            return "redirect:/dashboard"; 
        } else {
            model.addAttribute("error", "Invalid Credentials for Rusizi Hub");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/?logout";
    }
}