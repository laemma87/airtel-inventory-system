    AIRTEL ASSET MANAGEMENT SYSTEM

1. PROJECT OVERVIEW

The Airtel Asset Management System is a professional Desktop 
application designed to streamline the tracking, issuance, 
and maintenance of technical assets at the Airtel Hub. 
Built using Java Swing and Spring Boot, it provides a 
high-performance interface for real-time inventory control.

2. LOGIN CREDENTIALS (SECURITY)

Access to the system is restricted. Use the following 
authorized credentials to access the dashboard:

USERNAME: 24RP03266
PASSWORD: 24RP05628

3. TECHNOLOGY STACK

- Backend: Spring Boot (Core, JDBC)
- Frontend: Java Swing (Custom UI Components)
- Database: MariaDB / MySQL
- Connectivity: Spring JdbcTemplate
- IDE: Eclipse / IntelliJ IDEA

4. CORE FEATURES

- Centralized Dashboard: Instant visibility of total asset 
  counts and registered staff.
- Asset Lifecycle: Full CRUD functionality for hardware 
  tracking (Register, Edit, Delete).
- Issue/Return Logic: Automated workflow for assigning 
  devices to staff or returning them to the store.
- Staff Directory: Management of employee profiles and 
  department assignments.
- Reporting Hub: Dedicated sidebar access to generate 
  Inventory and Staffing summaries.
- Modern UI: Dark-themed sidebar and Airtel branding.

5. DATABASE STRUCTURE (Database: airtel_inventory)

The system utilizes the following verified tables:

- assets: 
  Fields: id (PK), tagId, serialNumber (UNIQUE), deviceType, 
  model, deviceCondition, status, assignedTo.

- contacts: 
  Fields: id (PK), employeeId (UNIQUE), name, department, 
  phone.

- users: 
  Fields: id (PK), username (UNIQUE), password, full_name, role.

6. INSTALLATION & SETUP

1. Create a MariaDB database named 'airtel_inventory'.
2. Execute the verified SQL schema (provided in the docs).
3. Update 'application.properties' with your DB username 
   and password.
4. Build the project using Maven (mvn clean install).
5. Run 'AirtelInventoryApplication.java'.

7. DEVELOPMENT TEAM


Lead Developers:
1. HAKIZIMANA Emmanuel : 24RP03266
2. INEZA Christa      : 24RP05628
