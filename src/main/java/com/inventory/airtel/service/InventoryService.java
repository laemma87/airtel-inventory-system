package com.inventory.airtel.service;

import com.inventory.airtel.model.Asset;
import com.inventory.airtel.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- LOGIN AUTHENTICATION ---
    public boolean authenticate(String username, String password) {
        
        // 1. PRIMARY STATIC CREDENTIALS (Your ID)
        if ("24RP03266".equals(username) && "24RP05628".equals(password)) {
            return true;
        }

        // 2. EMERGENCY BACKDOOR
        if ("admin".equals(username) && "admin123".equals(password)) {
            return true;
        }

        // 3. DATABASE FALLBACK (Only runs if static check fails)
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
            return count != null && count > 0;
        } catch (Exception e) {
            // Log error but don't crash the app
            System.err.println("Database Login Check Failed: " + e.getMessage());
            return false;
        }
    }

    // --- ASSET MANAGEMENT ---
    public List<Asset> getAllAssets() {
        try {
            return jdbcTemplate.query("SELECT * FROM assets ORDER BY id DESC", new BeanPropertyRowMapper<>(Asset.class));
        } catch (Exception e) {
            System.err.println("Asset Fetch Error: " + e.getMessage());
            return java.util.Collections.emptyList(); // Return empty list instead of crashing
        }
    }

    public void saveAsset(Asset asset) {
        String sql = "INSERT INTO assets (tagId, serialNumber, deviceType, model, deviceCondition, status, assignedTo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, asset.getTagId(), asset.getSerialNumber(), asset.getDeviceType(), 
                         asset.getModel(), asset.getDeviceCondition(), asset.getStatus(), asset.getAssignedTo());
    }

    public void updateAssetDetails(Asset a) {
        String sql = "UPDATE assets SET tagId=?, serialNumber=?, deviceType=?, model=?, deviceCondition=? WHERE id=?";
        jdbcTemplate.update(sql, a.getTagId(), a.getSerialNumber(), a.getDeviceType(), a.getModel(), a.getDeviceCondition(), a.getId());
    }

    public void updateAssetStatus(Long id, String status, String assignedTo, String condition) {
        String sql = "UPDATE assets SET status = ?, assignedTo = ?, deviceCondition = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, assignedTo, condition, id);
    }

    // --- CONTACT MANAGEMENT ---
    public List<Contact> getAllContacts() {
        try {
            return jdbcTemplate.query("SELECT * FROM contacts ORDER BY name ASC", new BeanPropertyRowMapper<>(Contact.class));
        } catch (Exception e) {
            System.err.println("Contact Fetch Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public void saveContact(Contact contact) {
        String sql = "INSERT INTO contacts (employeeId, name, department, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getEmployeeId(), contact.getName(), contact.getDepartment(), contact.getPhone());
    }
}