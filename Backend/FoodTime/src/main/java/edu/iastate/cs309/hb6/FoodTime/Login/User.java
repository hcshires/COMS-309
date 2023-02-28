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

    @Column (unique = true)
    private UUID UID;

    @OneToMany
    private List<Pantry> pantries;

    public User () {

    }

    @JsonCreator
    public User (String username, String password) {
        this.username = username;
        this.password = password;

        pantries = new ArrayList<>();

    }

    public void assignUID() {
        UID = UUID.randomUUID();
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
        return UID;
    }

    public List<Pantry> getPantries(){
        return pantries;
    }

    public void setPantries(List<Pantry> Pantry){
        this.pantries = pantries;
    }

    public void addPantries(Pantry pantry){
        this.pantries.add(pantry);
    }
}
