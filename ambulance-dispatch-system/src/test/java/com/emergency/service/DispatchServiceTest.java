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
    public void testSuccessfulAllocation() {
        // Register an available ambulance
        Ambulance amb = new Ambulance("AMB-01", AmbulanceType.BASIC, "John Doe", "Zone-A");
        dispatchService.registerAmbulance(amb);

        // Submit critical request
        EmergencyRequest req = new EmergencyRequest("REQ-01", "P-100", Priority.CRITICAL, "Zone-A", "CityHospital");
        dispatchService.submitEmergencyRequest(req);

        // Check if log records the successful dispatch
        List<String> logs = dispatchService.getHistoryLog();
        boolean hasDispatchedLog = logs.stream().anyMatch(log -> log.contains("dispatched to Request REQ-01"));
        
        assertTrue(hasDispatchedLog, "The emergency request should trigger an immediate ambulance dispatch configuration.");
    }

    @Test
    public void testPriorityQueueAndAllocation() {
        // No ambulances registered initially -> requests should queue up
        EmergencyRequest normalReq = new EmergencyRequest("REQ-NORM", "P-02", Priority.NORMAL, "Zone-B", "Hospital-X");
        EmergencyRequest criticalReq = new EmergencyRequest("REQ-CRIT", "P-01", Priority.CRITICAL, "Zone-C", "Hospital-Y");

        dispatchService.submitEmergencyRequest(normalReq);
        dispatchService.submitEmergencyRequest(criticalReq);

        // Register one ambulance to trigger the waiting queue extraction
        Ambulance amb = new Ambulance("AMB-FREE", AmbulanceType.ICU, "Jane Smith", "Zone-A");
        dispatchService.registerAmbulance(amb);

        // Force a status change to clear the top item from the priority queue
        dispatchService.updateAmbulanceState("AMB-FREE", AmbulanceState.AVAILABLE);

        // Check that the CRITICAL request was processed first due to priority sorting overrides
        List<String> logs = dispatchService.getHistoryLog();
        boolean criticalDispatchedFirst = logs.stream().anyMatch(log -> log.contains("Ambulance AMB-FREE dispatched to Request REQ-CRIT"));
        
        assertTrue(criticalDispatchedFirst, "The PriorityQueue must dispatch CRITICAL emergency requests before NORMAL requests.");
    }
}
