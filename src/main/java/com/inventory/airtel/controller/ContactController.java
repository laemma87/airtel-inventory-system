package com.inventory.airtel.controller;

import com.inventory.airtel.model.Contact;
import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private InventoryService inventoryService;

    // View all staff contacts
    @GetMapping("/contacts")
    public String listContacts(Model model) {
        List<Contact> contacts = inventoryService.getAllContacts();
        model.addAttribute("contacts", contacts);
        model.addAttribute("contact", new Contact()); // For the registration form
        return "contacts";
    }

    // Save a new staff member
    @PostMapping("/contacts/save")
    public String saveContact(@ModelAttribute("contact") Contact contact) {
        inventoryService.saveContact(contact);
        return "redirect:/contacts";
    }
}