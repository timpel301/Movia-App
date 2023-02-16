package de.teamtech.moviereservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class Controller {

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody String jsonPayload) {
        // Process the JSON payload here
        System.out.println("Received JSON payload: " + jsonPayload);
        
        // Return a response
        return new ResponseEntity<>("Booking created successfully!", HttpStatus.CREATED);
    }
}

