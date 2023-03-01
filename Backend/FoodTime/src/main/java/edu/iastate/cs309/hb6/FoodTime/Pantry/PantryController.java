package edu.iastate.cs309.hb6.FoodTime.Pantry;

/*
    @author Blake Hardy
 */

import java.util.List;

import edu.iastate.cs309.hb6.FoodTime.Login.User;
import edu.iastate.cs309.hb6.FoodTime.Login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;


@RestController
public class PantryController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PantryRepository pantryRepository;

    @GetMapping(path = "/pantry/getUserPantry") //specifies path to get to this controller I think
    @ResponseBody
    public ResponseEntity<Object> getUserPantry(@RequestParam String userID) { //requires that body contain a User object?

        if(pantryRepository.existsById(userID)) {
            return new ResponseEntity<>(pantryRepository.findById(userID), HttpStatus.OK); //returns pantry object and
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = "/pantry/getPantryString") //specifies path to get to this controller I think?
    @ResponseBody
    public ResponseEntity<Object> getUserPantryString(@RequestParam String userID) { //requires that json body contain a User object?

        if(pantryRepository.existsById(userID)) {
            return new ResponseEntity<>(pantryRepository.findByUID(userID).getIngredientListString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND);
    }



    @PutMapping(path = "/pantry/addToPantry")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> addToPantry(@RequestParam String userID, @RequestBody Ingredient ingredient) { //requires that json body contain a User object?

        if(pantryRepository.existsById(userID)) { //check to make sure user exists

            Pantry userPantry = pantryRepository.findByUID(userID);
            userPantry.addIngredient(ingredient);

            return new ResponseEntity<>(userPantry.getIngredientListString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("No such user associated with UID", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/pantry/removeFromPantry")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> removeFromPantry(@RequestParam String userID, @RequestParam String ingredientName) {

        if(pantryRepository.existsById(userID)) { //check to make sure user exists

            //delete function can handle nonexistant Ingredients, returns boolean
            if(pantryRepository.findByUID(userID).deleteIngredientByName(ingredientName)){ //returns true
                return new ResponseEntity<>(null, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND); //ingredient not found
            }
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found

    }
}

