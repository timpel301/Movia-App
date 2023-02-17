package de.teamtech.moviereservation.controller.controller;

// import static org.junit.Assert.assertEquals;
// import static org.mockito.Mockito.*;

// import org.json.JSONObject;
// import org.junit.Before;
// import org.junit.Test;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// public class ControllerApplicationTest {
//     private ControllerApplication controller;
    
//     @Before
//     public void setup() {
//         controller = new ControllerApplication();
//     }
    
//     @Test
//     public void testCreateBooking() {
//         // Create a mock JSON payload
//         JSONObject payload = new JSONObject();
//         payload.put("title", "Mr.");
//         payload.put("name", "John Doe");
//         payload.put("numberOfTickets", 2);
        
//         // Call the createBooking method with the mock payload
//         ResponseEntity<String> response = controller.createBooking(payload.toString());
        
//         // Check that the response has the correct status code and message
//         assertEquals(HttpStatus.CREATED, response.getStatusCode());
//         assertEquals("Booking created successfully!", response.getBody());
//     }
// }

