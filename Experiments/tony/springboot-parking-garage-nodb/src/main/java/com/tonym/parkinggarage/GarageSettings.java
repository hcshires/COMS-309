package com.tonym.parkinggarage;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.stereotype.Service;

@Service
public class GarageSettings {
    private boolean isOpen;
    private int capacity;

    //Default settings. Can be modified later by accessing prefs in Controller
    @JsonCreator
    public GarageSettings () {
        isOpen = true;
        capacity = 10;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
