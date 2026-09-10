package com.emergency.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.emergency.model.Ambulance;
import com.emergency.model.AmbulanceType;
import com.emergency.model.EmergencyPriority;
import com.emergency.model.EmergencyRequest;

public class DispatchServiceTest {
    private DispatchService dispatchService;

    @BeforeEach
    public void setUp() {
        dispatchService = new DispatchService();
    }

    @Test
    public void testEmergencySystemWorkflow() {
        // 1. Initialize Ambulance matching: (String, AmbulanceType, String, double, double)
        Ambulance amb = new Ambulance("AMB-01", AmbulanceType.BASIC, "John Doe", 12.9716, 79.1588);
        dispatchService.registerAmbulance(amb);

        // 2. Initialize EmergencyRequest matching your exact signature:
        // (String, String, EmergencyPriority, AmbulanceType, double, double, String)
        EmergencyRequest req = new EmergencyRequest(
            "REQ-01", 
            "Medical", 
            EmergencyPriority.CRITICAL, 
            AmbulanceType.BASIC, 
            12.9720, 
            79.1595, 
            "City Hospital"
        );
        dispatchService.submitEmergencyRequest(req);

        // 3. Verify that the operation was captured by the tracking ledger
        List<String> logs = dispatchService.getHistoryLog();
        assertNotNull(logs, "History log must be initialized.");
        assertFalse(logs.isEmpty(), "The application must record emergency tracking history logs.");
    }
}
