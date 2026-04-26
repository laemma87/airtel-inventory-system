package com.inventory.airtel.service;

import com.inventory.airtel.model.Asset;
import com.inventory.airtel.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

@Service
public class InventoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- ASSET MANAGEMENT (Corrected Save Logic) ---
    
    public void saveAsset(Asset asset) {
        // Corrected Column Order to match the Object fields exactly
        String sql = "INSERT INTO assets (tagId, deviceType, serialNumber, model, deviceCondition, status, assignedTo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            asset.getTagId(), 
            asset.getDeviceType(), 
            asset.getSerialNumber(), 
            asset.getModel(), 
            asset.getDeviceCondition(), 
            (asset.getStatus() != null) ? asset.getStatus() : "In Store", 
            (asset.getAssignedTo() != null) ? asset.getAssignedTo() : "Unassigned"
        );
    }

    public void updateAssetDetails(Asset a) {
        String sql = "UPDATE assets SET tagId=?, deviceType=?, serialNumber=?, model=?, deviceCondition=? WHERE id=?";
        jdbcTemplate.update(sql, 
            a.getTagId(), 
            a.getDeviceType(), 
            a.getSerialNumber(), 
            a.getModel(), 
            a.getDeviceCondition(), 
            a.getId()
        );
    }

    // Keep your other methods as they are...
    
    public List<Asset> getAllAssets() {
        try {
            return jdbcTemplate.query("SELECT * FROM assets ORDER BY id DESC", new BeanPropertyRowMapper<>(Asset.class));
        } catch (Exception e) {
            System.err.println("Asset Fetch Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Asset getAssetById(Long id) {
        try {
            String sql = "SELECT * FROM assets WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Asset.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateAssetStatus(Long id, String status, String assignedTo, String condition) {
        String sql = "UPDATE assets SET status = ?, assignedTo = ?, deviceCondition = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, assignedTo, condition, id);
    }

    public boolean authenticate(String username, String password) {
        if ("24RP03266".equals(username) && "24RP05628".equals(password)) return true;
        if ("admin".equals(username) && "admin123".equals(password)) return true;
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Contact> getAllContacts() {
        try {
            return jdbcTemplate.query("SELECT * FROM contacts ORDER BY name ASC", new BeanPropertyRowMapper<>(Contact.class));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void saveContact(Contact contact) {
        String sql = "INSERT INTO contacts (employeeId, name, department, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getEmployeeId(), contact.getName(), contact.getDepartment(), contact.getPhone());
    }
}