package com.tonym.parkinggarage;

import com.tonym.parkinggarage.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class ParkingGarageController {

    ParkingGarage garage;

    @Autowired
    GarageSettings prefs;

    @PutMapping("/configure")
    public void configureGarage(@RequestBody GarageSettings prefs) {
        this.prefs = prefs;
        garage = new ParkingGarage();
    }

    @PostMapping("/vehicles/add")
    public String addVehicle(@RequestBody Vehicle vehicle) {
        if (vehicle != null && garage.count() < prefs.getCapacity()) {
            garage.addVehicle(vehicle);
            return "Added " + vehicle;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle is null or garage is full");
    }

    @PutMapping("vehicles/update/{plate}")
    public String updateVehicle(@PathVariable String plate, @RequestBody Vehicle request) {
        if (!garage.updateVehicle(plate, request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempted to update nonexistent entry");
        }
        return "Updated " + request;
    }

    @DeleteMapping("vehicles/delete/{plate}")
    public String deleteVehicle(@PathVariable String plate) {
        if (garage.deleteVehicle(plate)) {
            return "Deleted vehicle associated with plate " + plate;
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempted to delete element that does not exist");
        }
    }

    @DeleteMapping("/vehicles/tow")
    @ResponseBody
    public ArrayList<Vehicle> towWithoutPermit() {
        return garage.towWithoutPermit();
    }
}
