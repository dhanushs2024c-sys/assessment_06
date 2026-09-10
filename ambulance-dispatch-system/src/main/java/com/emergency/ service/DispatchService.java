package com.emergency.service;

import java.util.ArrayList;
import java.util.List;

import com.emergency.exception.ResourceUnavailableException;
import com.emergency.model.Ambulance;
import com.emergency.model.EmergencyRequest;

public class DispatchService {
    private List<Ambulance> ambulances = new ArrayList<>();
    private List<String> historyLog = new ArrayList<>();

    public void registerAmbulance(Ambulance ambulance) {
        if (ambulance == null) {
            throw new ResourceUnavailableException("Cannot register a null ambulance resource.");
        }
        ambulances.add(ambulance);
        historyLog.add("Ambulance registered successfully");
    }

    public void submitEmergencyRequest(EmergencyRequest request) {
        if (request == null) {
            throw new ResourceUnavailableException("Invalid or empty emergency request payload.");
        }
        historyLog.add("Emergency Request received and logged");
    }

    public List<String> getHistoryLog() {
        return historyLog;
    }
}
