# ArunSampleDBApplication

This REST based DB application is covered by General public license v3 and it is intended to be used for demo purposes only.
The project can be used by any organization provided the author name (Arun Nallan) and [GitHub URL](https://github.com/arunnc/ArunSampleDBApplication) is included in the comments section of the imported code.

## Algorithm

- Its just a plain demo purpose to demonstrate Graph database with linear operation. Time is not spent enough for performance optimization like search operations - however, best effort is made.
- All queries are case sensitive.
- Since this is a REST application - it uses error codes like 400 for bad request, 404 for resource not found and 200 for success, etc., following the standard convention.
- At this time, the application does not support *BULK Import/Export* but can be easily extended to serialize/de-serialize the in-memory database from/to JSON file 

## Pre-requisites

1. JDK 1.8
2. Spring Tool Suite (STS) or Eclipse with Maven compatibility
3. Network connection for importing Maven dependencies

## Steps to import.

1. Import the project into Eclipse as a Maven Project
2. Right click on the imported project from Eclipse
3. Note that the server.port is set to 8765 port by default in src/main/resources/application.properties
4. Edit server.port to a different port if your network is not open for 8765 port number or work with Network admins to open the  firewall port.
5. Run as Spring Boot Application or Java application


## Testing the application

- Use Postman or Chrome plugin - Advanced Rest Client to test the below urls

 - Please make sure to pass the below in Request Headers for all the requests:
   ```
   Request Headers
   Content-Type: application/json
   ```
 
 - POST http://localhost:8765/customers/createOrUpdate
   ```
   Request Body
   {"id": "3", "name": "Tharak Nallan", "age": "8", "belongsToRegion": {"name": "Madurai"}}
   ```
 - GET  http://localhost:8765/customers
 - GET  http://localhost:8765/customers/{id or name}
 - GET  http://localhost:8765/regions
 - GET  http://localhost:8765/regions/Madurai
 - GET  http://localhost:8765/regions/Madurai/customers
 - GET  http://localhost:8765/regions/Madurai/customers?ageFrom=30&ageTo=40
 
 