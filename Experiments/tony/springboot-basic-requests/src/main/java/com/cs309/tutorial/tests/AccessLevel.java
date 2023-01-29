package com.cs309.tutorial.tests;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AccessLevel {
    private boolean admin;

    //Necessary so that @RequestBody can properly deserialize
    @JsonCreator
    public AccessLevel(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }
}
