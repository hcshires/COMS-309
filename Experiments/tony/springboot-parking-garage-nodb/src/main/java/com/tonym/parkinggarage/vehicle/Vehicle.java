package com.tonym.parkinggarage.vehicle;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Vehicles")
public class Vehicle {

    @Column(name = "Make")
    private String make;

    @Column(name = "Model")
    private String model;

    @Id
    @Column(name = "Plate")
    private String plate;

    @Column(name = "Has Permit")
    private boolean hasPermit;

    @JsonCreator
    public Vehicle(String make, String model, String plate, boolean hasPermit) {
        this.make = make;
        this.model = model;
        this.plate = plate;
        this.hasPermit = hasPermit;
    }

    public Vehicle() {

    }

    public boolean hasPermit() {
        return hasPermit;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setHasPermit(boolean hasPermit) {
        this.hasPermit = hasPermit;
    }

    @Override
    public String toString() {
        return String.format("%s %s\nPlate: %s\nPermit valid: %b\n", make, model, plate, hasPermit);
    }
}
