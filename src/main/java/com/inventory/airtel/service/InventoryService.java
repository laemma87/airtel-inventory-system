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
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // --- ASSET MANAGEMENT ---
    public List<Asset> getAllAssets() {
        return jdbcTemplate.query("SELECT * FROM assets ORDER BY id DESC", new BeanPropertyRowMapper<>(Asset.class));
    }

    public Asset getAssetById(Long id) {
        String sql = "SELECT * FROM assets WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Asset.class), id);
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

    public void deleteAsset(Long id) {
        jdbcTemplate.update("DELETE FROM assets WHERE id = ?", id);
    }

    // --- CONTACT MANAGEMENT ---
    public List<Contact> getAllContacts() {
        return jdbcTemplate.query("SELECT * FROM contacts ORDER BY name ASC", new BeanPropertyRowMapper<>(Contact.class));
    }

    public void saveContact(Contact contact) {
        String sql = "INSERT INTO contacts (employeeId, name, department, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getEmployeeId(), contact.getName(), contact.getDepartment(), contact.getPhone());
    }

    public void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET employeeId=?, name=?, department=?, phone=? WHERE id=?";
        jdbcTemplate.update(sql, contact.getEmployeeId(), contact.getName(), contact.getDepartment(), contact.getPhone(), contact.getId());
    }

    public void deleteContact(Long id) {
        jdbcTemplate.update("DELETE FROM contacts WHERE id = ?", id);
    }
}