package edu.iastate.cs309.hb6.FoodTime.Login;


import edu.iastate.cs309.hb6.FoodTime.Meal.Meal;
import edu.iastate.cs309.hb6.FoodTime.Meal.MealList;
import edu.iastate.cs309.hb6.FoodTime.Meal.MealRepository;
import edu.iastate.cs309.hb6.FoodTime.Pantry.Pantry;
import edu.iastate.cs309.hb6.FoodTime.Pantry.PantryRepository;
import edu.iastate.cs309.hb6.FoodTime.Preferences.UserPreferencesRepository;
import edu.iastate.cs309.hb6.FoodTime.Preferences.UserPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserRepository userDB;

    @Autowired
    UserPreferencesRepository prefsDB;

    @Autowired
    PantryRepository pantryDB;

    @Autowired
    MealRepository mealDB;

    @PostMapping("/users/create")
    @ResponseBody
    @Transactional
    //We can return an HTTP response as well as a UID after creating the user
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (!userDB.existsByUsername(user.getUsername())) {
            //Create a user if they do not exist in the system
            user.assignUID();

            //Assign them default preferences
            UserPreferences prefs = new UserPreferences(user.getUID());

            //Assign them a pantry entry
            Pantry userPantry = new Pantry (user.getUID().toString());

            //Create their weekly list of meals
            MealList userMeals = new MealList(user.getUID());

            //User's recipe book
            Map<String, Meal> userRecipeBook = new HashMap<>();

            //List of meal labels, only for frontend, backend do not use these
            ArrayList<String> userRecipeLabels = new ArrayList<>();

            user.setUserPreferences(prefs);
            user.setUserPantry(userPantry);
            user.setUserMeals(userMeals);
            user.setRecipeLabels(userRecipeLabels);
            //User recipes is set in the User constructor
            userPantry.setUser(user);
            prefs.setUser(user);
            userMeals.setUser(user);

            userDB.save(user);
            prefsDB.save(prefs);
            pantryDB.save(userPantry);
            mealDB.save(userMeals);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/users/login")
    @ResponseBody
    public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password) {
        User lookup = userDB.findByUsername(username);

        if (lookup.getPassword().equals(password)) {
            return new ResponseEntity<>(lookup.getUID(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/delete")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> deleteUser(@RequestBody User user) {
        if (userDB.existsByUsername(user.getUsername()) && userDB.findByUsername(user.getUsername()).getPassword().equals(user.getPassword())) {
            User deletedUser = userDB.findByUsername(user.getUsername());
            userDB.deleteByUsername(user.getUsername());
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/users/password-reset")
    @Transactional
    public ResponseEntity<Object> updatePassword(@RequestParam String username, @RequestParam String newPassword) {
        if (userDB.existsByUsername(username)) {
            User userToUpdate = userDB.findByUsername(username);
            //We do not need to call a save on the database for this entry since the method is marked as transactional
            //When the method call exits the data is automatically flushed to the DB
            userToUpdate.setPassword(newPassword);
            return new ResponseEntity<>(userToUpdate, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
