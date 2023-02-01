package com.tonym.parkinggarage;

import com.tonym.parkinggarage.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkingGarage {
    private HashMap<String, Vehicle> garage;

    public ParkingGarage() {
        garage = new HashMap<>();
    }

    public void addVehicle(Vehicle vehicle) {
        garage.put(vehicle.getPlate(), vehicle);
    }

    public boolean updateVehicle(String plate, Vehicle vehicle) {
        Vehicle success = garage.replace(plate, vehicle);
        return success != null;
    }

    public boolean deleteVehicle(String plate) {
        Vehicle success =  garage.remove(plate);
        return success != null;
    }

    public ArrayList<Vehicle> towWithoutPermit() {
        ArrayList<Vehicle> carsTowed = new ArrayList<>();

        garage.forEach((plate, vehicle) -> {
            if (!vehicle.hasPermit()) carsTowed.add(vehicle);
            garage.remove(plate);
        });
        return carsTowed;
    }

    public int count() {
        return garage.size();
    }
}
