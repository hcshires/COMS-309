package edu.iastate.cs309.hb6.FoodTime.Pantry;

/*
    @author Blake Hardy

 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.cs309.hb6.FoodTime.Login.User;

import javax.persistence.*;

@Entity
public class Pantry {

    //instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    //maybe add some sort of catagory system? fruit, veg, starch, meat, etc? can do later.

    //to create relation between a User and this object
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    //now for methods


    //constructor

    public Pantry(String name){
        this.name = name;

    }

    public Pantry(){}

    //basic get/set methods

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }




}
