package de.teamtech.moviereservation.controller.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ControllerApplicationTests {

    @Mock
    private Connection mockConnection;

    @Test
    public void testCreateBooking() {
        MockitoAnnotations.openMocks(this);
        ControllerApplication controller = new ControllerApplication();

        // Mock the database connection
        try {
            when(mockConnection.prepareStatement(anyString())).thenReturn(null);
            when(mockConnection.createStatement()).thenReturn(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Call the createBooking method with a sample JSON payload
        String jsonPayload = "{\"title\":\"The Matrix\",\"name\":\"John Smith\",\"numberOfTickets\":2}";
        ResponseEntity<String> responseEntity = controller.createBooking(jsonPayload);

        // Verify that the response is successful
        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
    }
}