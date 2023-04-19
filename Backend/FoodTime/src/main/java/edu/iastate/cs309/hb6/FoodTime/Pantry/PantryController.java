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
    private PantryRepository pantryRepository;

    @GetMapping(path = "/pantry/getUserPantry") //specifies path to get to this controller I think
    @ResponseBody
    public ResponseEntity<Object> getUserPantry(@RequestParam String UID) { //requires that body contain a User object?

        if (userRepository.existsById(UID)) {
            return new ResponseEntity<>(userRepository.findByUID(UID).getUserPantry(), HttpStatus.OK); //returns pantry object and
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = "/pantry/getUserPantryString") //specifies path to get to this controller I think?
    @ResponseBody
    public ResponseEntity<Object> getUserPantryString(@RequestParam String UID) { //requires that json body contain a User object?

        if (userRepository.existsById(UID)) {
            return new ResponseEntity<>(userRepository.findByUID(UID).getUserPantry().getIngredientListString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND);
    }


    @PutMapping(path = "/pantry/addToPantry")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> addToPantry(@RequestParam String UID, @RequestParam String ingredientName, @RequestParam int quantity, @RequestParam String unitsType) { //requires that json body contain a User object?
        Ingredient ingredient = new Ingredient(ingredientName, quantity, unitsType);
        if (userRepository.existsById(UID)) { //check to make sure user exists
            Pantry userPantry = userRepository.findByUID(UID).getUserPantry();
            userPantry.addIngredient(ingredient);

            return new ResponseEntity<>(userPantry.getIngredientListString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("No such user associated with UID", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/pantry/removeFromPantry")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> removeFromPantry(@RequestParam String UID, @RequestParam String ingredientName) {

        if (userRepository.existsById(UID)) { //check to make sure user exists

            //delete function can handle nonexistant Ingredients, returns boolean
            if (userRepository.findByUID(UID).getUserPantry().deleteIngredientByName(ingredientName)) { //returns true
                return new ResponseEntity<>(null, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND); //ingredient not found
            }
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found

    }


    //get and put quantity and quantity type. not implementing any sort of protections from end user stupid
    //going to just case-insensitive string match. mistakes will be annoying for user but shouldn't break anything

    //TODO refactor controllers and pantry to use new utility methods

    @GetMapping(path = "/pantry/getQuantity")
    @ResponseBody
    public ResponseEntity<Object> getQuantity(@RequestParam String UID, @RequestParam String ingredientName){

        if(userRepository.existsById(UID)){
            if(userRepository.findByUID((UID)).getUserPantry().hasIngredient(ingredientName)){

                return new ResponseEntity<>(userRepository.findByUID(UID).getUserPantry().getQuantity(ingredientName), HttpStatus.OK);
            }
            return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found
    }

    @PutMapping(path = "/pantry/setQuantity")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> setQuantity(@RequestParam String UID, @RequestParam String ingredientName, @RequestParam int quantity){

        if(userRepository.existsById(UID)){
            if(userRepository.findByUID((UID)).getUserPantry().hasIngredient(ingredientName)){

                pantryRepository.findByUID(UID).getIngredientByName(ingredientName).setQuantity(quantity);
                return new ResponseEntity<>(pantryRepository.findByUID(UID).getQuantity(ingredientName), HttpStatus.OK);
            }
            return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found
    }

    //get and put for quantity Type

    @GetMapping(path = "/pantry/getQuantityType")
    @ResponseBody
    public ResponseEntity<Object> getQuantityType(@RequestParam String UID, @RequestParam String ingredientName){

        if(userRepository.existsById(UID)) {
            if (userRepository.findByUID((UID)).getUserPantry().hasIngredient(ingredientName)) {

                return new ResponseEntity<>(userRepository.findByUID(UID).getUserPantry().getQuantityType(ingredientName), HttpStatus.OK);

            }
            return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found
    }

    @PutMapping(path = "/pantry/setQuantityType")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> setQuantityType(@RequestParam String UID, @RequestParam String ingredientName, @RequestParam String quantityType){


        if(userRepository.existsById(UID)) {
            if (userRepository.findByUID((UID)).getUserPantry().hasIngredient(ingredientName)) {
                userRepository.findByUID(UID).getUserPantry().setQuantityType(ingredientName, quantityType);

                return new ResponseEntity<>(userRepository.findByUID(UID).getUserPantry().getQuantityType(ingredientName), HttpStatus.OK);
            }
            return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND);
    }


}
