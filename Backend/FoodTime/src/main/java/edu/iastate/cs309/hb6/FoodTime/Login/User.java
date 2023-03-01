package edu.iastate.cs309.hb6.FoodTime.Login;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.*;
import java.util.UUID;
import edu.iastate.cs309.hb6.FoodTime.Pantry.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @Column (unique = true)
    private String username;

    @Column
    private String password;

    //This is a string because UUIDs do not play super nicely in the DB
    @Column (unique = true)
    private String UID;

    @Embedded
    @Column
    private Pantry pantry;

    public User () {

    }

    @JsonCreator
    public User (String username, String password) {
        this.username = username;
        this.password = password;

        pantry = new Pantry(); //TODO idk if this should be here

    }

    public void assignUID() {
        UID = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUID() {
        return java.util.UUID.fromString(UID);
    }

    public Pantry getPantry(){
        return this.pantry;
    }

    public void setPantries(Pantry pantry){
        this.pantry = pantry;
    }
}
