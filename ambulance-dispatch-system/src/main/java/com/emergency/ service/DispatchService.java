package com.emergency.service;

import com.emergency.model.Ambulance;
import com.emergency.model.EmergencyRequest;
import com.emergency.exception.ResourceUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class DispatchService {
    private List<Ambulance> ambulances = new ArrayList<>();
    private List<String> historyLog = new ArrayList<>();

    public void registerAmbulance(Ambulance ambulance) {
        if (ambulance == null) {
            throw new ResourceUnavailableException("Cannot register a null ambulance resource.");
        }
        ambulances.add(ambulance);
        historyLog.add("Ambulance registered: " + ambulance.getId());
    }

    public void submitEmergencyRequest(EmergencyRequest request) {
        if (request == null) {
            throw new ResourceUnavailableException("Invalid or empty emergency request payload.");
        }
        historyLog.add("Emergency Request received: " + request.getId());
    }

    public List<String> getHistoryLog() {
        return historyLog;
    }
}
