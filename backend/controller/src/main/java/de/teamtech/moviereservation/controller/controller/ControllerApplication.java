package de.teamtech.moviereservation.controller.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@SpringBootApplication
public class ControllerApplication {

    @PostMapping("/booking")
    public ResponseEntity<String> createBooking(@RequestBody String jsonPayload) {
        // Extract data from the JSON payload
        JSONObject jsonObj = new JSONObject(jsonPayload);
        String title = jsonObj.getString("title");
        String name = jsonObj.getString("name");
        int numberOfTickets = jsonObj.getInt("numberOfTickets");

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        // Database connection
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://20.23.133.107:5432/postgres";
            conn = DriverManager.getConnection(url, "root", "mypassword");

            // database table
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "bookings", null);
            if (!rs.next()) {
            // Table does not exist, create it
            stmt = conn.createStatement();
            String sql = "CREATE TABLE bookings (id SERIAL PRIMARY KEY, title VARCHAR(255), name VARCHAR(255), number_of_tickets INTEGER)";
            stmt.executeUpdate(sql);
            }

            // inpute data into database
            String sql = "INSERT INTO bookings (title, name, number_of_tickets) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, name);
            pstmt.setInt(3, numberOfTickets);
            pstmt.executeUpdate();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: database driver not found", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: database error", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        // Return a response
        return new ResponseEntity<>("Booking created successfully!", HttpStatus.CREATED);
    }

    // dummy get request
    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }


    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class, args);
        
    }

}

