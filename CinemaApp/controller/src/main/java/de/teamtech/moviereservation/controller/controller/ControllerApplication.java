package de.teamtech.moviereservation.controller.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/booking")
@SpringBootApplication
public class ControllerApplication {

	@PostMapping
    public ResponseEntity<String> createBooking(@RequestBody String jsonPayload) {
        // Extract data from the JSON payload
        JSONObject jsonObj = new JSONObject(jsonPayload);
        String title = jsonObj.getString("title");
        String name = jsonObj.getString("name");
        int numberOfTickets = jsonObj.getInt("numberOfTickets");

        // Insert the data into the database
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/moviereservation";
            String username = "your_username";
            String password = "your_password";
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO bookings (title, name, number_of_tickets) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, name);
            pstmt.setInt(3, numberOfTickets);
            pstmt.executeUpdate();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: database driver not found", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: database error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return a response
        return new ResponseEntity<>("Booking created successfully!", HttpStatus.CREATED);
    }


	public static void main(String[] args) {
		SpringApplication.run(ControllerApplication.class, args);
	}

}

