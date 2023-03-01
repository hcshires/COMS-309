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

    //create relation between a User and pantry object I think?
    //yeah not really sure what this does, but I dont think it does what I want it to. but I think it should be functional at least?
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /*
    TODO should restructure this whole thing probably. it doesn't make much sense at the moment,
    if I understand it right, we will need to create a neutral pantry object, then assign it an owner via its ID
    rather than calling on the owner to create it's own pantry object.
    which would be ideal, but I dont currently know how to do that and I dont want to start changing shit right before the demo
     */


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
