package com.emergency.service;

import com.emergency.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class DispatchServiceTest {
    private DispatchService dispatchService;

    @BeforeEach
    public void setUp() {
        dispatchService = new DispatchService();
    }

    @Test
    public void testEmergencySystemWorkflow() {
        // 1. Initialize an Ambulance using your exact code requirement (String, AmbulanceType, String, double, double)
        Ambulance amb = new Ambulance("AMB-01", AmbulanceType.BASIC, "John Doe", 12.9716, 79.1588);
        dispatchService.registerAmbulance(amb);

        // 2. Submit an Emergency Request using the parameters specified by your engine
        // Using high/critical designations matching your specific model definition
        EmergencyRequest req = new EmergencyRequest("REQ-01", "P-100", com.emergency.model.Priority.CRITICAL, "Location-A", "Hospital-X");
        dispatchService.submitEmergencyRequest(req);

        // 3. Verify that the operation was captured by the historical tracking ledger
        List<String> logs = dispatchService.getHistoryLog();
        assertNotNull(logs, "History log must be initialized.");
        
        boolean hasActivity = !logs.isEmpty();
        assertTrue(hasActivity, "The application must record emergency tracking history logs.");
    }
}
