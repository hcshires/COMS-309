package com.tonym.parkinggarage;

import com.tonym.parkinggarage.vehicle.Vehicle;

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
        });

        for (Vehicle illegal : carsTowed) {
            garage.remove(illegal.getPlate());
        }
        return carsTowed;
    }

    public Vehicle getVehicleByPlate (String plate) {
        return garage.get(plate);
    }

    public int count() {
        return garage.size();
    }
}
