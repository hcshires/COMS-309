package edu.iastate.cs309.hb6.FoodTime.Meal;

import edu.iastate.cs309.hb6.FoodTime.Login.User;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.HashMap;
import java.util.UUID;

@Entity
@Table(name = "weekly_meal_list")
public class MealList {

    @Id
    @Column(unique = true)
    private String UID;

    @OneToOne(mappedBy = "userMealsWeekly")
    private User user;

    //Each hash map will contain a number of meals that the user wishes to make on that day
    @Column (name = "sunday")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> sunday;
    @Column (name = "monday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> monday;
    @Column (name = "tuesday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> tuesday;
    @Column (name = "wednesday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> wednesday;
    @Column (name = "thursday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> thursday;
    @Column (name = "friday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> friday;
    @Column (name = "saturday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> saturday;

    public MealList () {

    }

    public MealList (UUID UID) {
        this.UID = UID.toString();
        sunday = new HashMap<>();
        monday = new HashMap<>();
        tuesday = new HashMap<>();
        wednesday = new HashMap<>();
        thursday = new HashMap<>();
        friday = new HashMap<>();
        saturday = new HashMap<>();
    }

    public HashMap<String, Meal> getMealsForDay(String day) {
        switch (day) {
            case "sunday": return sunday;
            case "monday": return monday;
            case "tuesday": return tuesday;
            case "wednesday": return wednesday;
            case "thursday": return thursday;
            case "friday": return friday;
            case "saturday": return saturday;
            default: return null;
        }
    }

    public void setMealsForDay(String day, HashMap<String, Meal> newList) {
        switch (day) {
            case "sunday": sunday = newList;
            case "monday": monday = newList;
            case "tuesday": tuesday = newList;
            case "wednesday": wednesday = newList;
            case "thursday": thursday = newList;
            case "friday": friday = newList;
            case "saturday": saturday = newList;
        }
    }

    public HashMap<String, Meal> getSunday() {
        return sunday;
    }

    public HashMap<String, Meal> getMonday() {
        return monday;
    }

    public HashMap<String, Meal> getTuesday() {
        return tuesday;
    }

    public HashMap<String, Meal> getWednesday() {
        return wednesday;
    }

    public HashMap<String, Meal> getThursday() {
        return thursday;
    }

    public HashMap<String, Meal> getFriday() {
        return friday;
    }

    public HashMap<String, Meal> getSaturday() {
        return saturday;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
