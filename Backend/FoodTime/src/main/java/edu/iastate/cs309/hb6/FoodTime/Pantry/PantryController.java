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
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
public class PantryController {

    //still not sure what this does
    @Autowired
    UserRepository userRepository;

//    private String success = "{\"message\":\"success\"}";
//    private String failure = "{\"message\":\"failure\"}";

    //should probably put these controllers with User, as they mostly opperate on pantries through the user rather than directly

    //should I request just the userID instead? might make this easier to understand
    //can use userRepository.getReferenceByID(int id). might simplify the requests a bit
    //wouldn't even need a request body that way, can do everything in the PATH

    //also Im not sure if we even need the PantryRepository if we're just storing everything associated with the user anyway
    //I need to make sure I understand what Im doing before I try to do it next time lol


    //given the path "/pantry" and a user, return the Pantry object associated with it.
    // might add options to return info as an array of name strings or other data
    //I hope I'm understanding this right
    @GetMapping(path = "/pantry/getUserPantry") //specifies path to get to this controller I think
    @ResponseBody
    public ResponseEntity<Object> getUserPantry(@RequestParam String user) { //requires that body contain a User object?

        if(userRepository.existsByUsername(user)) {
            return new ResponseEntity<>(userRepository.findByUsername(user).getPantry(), HttpStatus.OK); //returns pantry object and
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }



    @GetMapping(path = "/pantry/getPantryString") //specifies path to get to this controller I think?
    @ResponseBody
    public ResponseEntity<Object> getUserPantryString(@RequestParam String user) { //requires that json body contain a User object?

        if(userRepository.existsByUsername(user)) {
            return new ResponseEntity<>(userRepository.findByUsername(user).getPantry().getIngredientListString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND);
    }



    @PutMapping(path = "/pantry/addToPantry")
    @ResponseBody
    public ResponseEntity<Object> addToPantry(@RequestParam String user, @RequestBody Ingredient ingredient) { //requires that json body contain a User object?

        if(userRepository.existsByUsername(user)) { //check to make sure user exists

            Pantry pantry = userRepository.findByUsername(user).getPantry();
            pantry.addIngredient(ingredient);

            return new ResponseEntity<>(userRepository.findByUsername(user).getPantry().getIngredientListString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/pantry/removeFromPantry")
    @ResponseBody
    public ResponseEntity<Object> removeFromPantry(@RequestParam String user, @RequestBody String ingredient) {

        if(userRepository.existsByUsername(user)) { //check to make sure user exists

            //delete function can handle nonexistant Ingredients, returns boolean
            if(userRepository.findByUsername(user).getPantry().deleteIngredientByName(ingredient)){ //returns true
                return new ResponseEntity<>(null, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND); //ingredient not found
            }
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found

    }
}

