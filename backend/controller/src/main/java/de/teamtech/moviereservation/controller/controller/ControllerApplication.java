package de.teamtech.moviereservation.controller.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;

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
import java.util.Base64;
import java.util.Map;

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

        // Insert the data into the database
		Config config = new ConfigBuilder().build();
		KubernetesClient client = new DefaultKubernetesClient(config);
		Secret secret = client.secrets().inNamespace("teamtech-ns").withName("postgres-secret").get();

		Map<String, String> data = secret.getData();
		String username = new String(Base64.getDecoder().decode(data.get("username")));
		String password = new String(Base64.getDecoder().decode(data.get("password")));


        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://20.4.203.253:5432/tech-db";
            Connection conn = DriverManager.getConnection(url, username, password);

            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "bookings", null);
            if (!rs.next()) {
            // Table does not exist, create it
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE bookings (id SERIAL PRIMARY KEY, title VARCHAR(255), name VARCHAR(255), number_of_tickets INTEGER)";
            stmt.executeUpdate(sql);
            }

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

    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }


    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class, args);
        
    }

}

