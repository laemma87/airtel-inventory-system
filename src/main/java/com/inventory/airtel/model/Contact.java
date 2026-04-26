package com.inventory.airtel.model;

/**
 * Model class for Staff Contacts.
 * Compatible with JdbcTemplate and BeanPropertyRowMapper.
 */
public class Contact {
    private Long id;
    private String employeeId;
    private String name;
    private String department;
    private String phone;

    // Default constructor is required for Spring to map database rows to this object
    public Contact() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}