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
        User user = userRepository.findByUID(UID);

        if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
            return new ResponseEntity<>(user.getUserPantry(), HttpStatus.OK); //returns pantry object and
        }
        else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
            return new ResponseEntity<>(user.getParentUser().getUserPantry(), HttpStatus.OK);
        }
        else return new ResponseEntity<>("Invalid AccessType for user", HttpStatus.BAD_REQUEST);
    }


    @GetMapping(path = "/pantry/getUserPantryString") //specifies path to get to this controller I think?
    @ResponseBody
    public ResponseEntity<Object> getUserPantryString(@RequestParam String UID) { //requires that json body contain a User object?
        User user = userRepository.findByUID(UID);

        if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
            return new ResponseEntity<>(user.getUserPantry().getIngredientListString(), HttpStatus.OK); //returns pantry object and
        }
        else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
            return new ResponseEntity<>(user.getParentUser().getUserPantry().getIngredientListString(), HttpStatus.OK);
        }
        else return new ResponseEntity<>("Invalid AccessType for user", HttpStatus.BAD_REQUEST);
    }


    @PutMapping(path = "/pantry/addToPantry")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> addToPantry(@RequestParam String UID, @RequestParam String ingredientName, @RequestParam int quantity, @RequestParam String unitsType) { //requires that json body contain a User object?
        Ingredient ingredient = new Ingredient(ingredientName, quantity, unitsType);
        User user = userRepository.findByUID(UID);

        if (user != null) { //check to make sure user exists
            if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
                Pantry userPantry = user.getUserPantry();
                userPantry.addIngredient(ingredient);
                return new ResponseEntity<>(userPantry.getIngredientListString(), HttpStatus.OK);
            }
            else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
                return new ResponseEntity<>("Child user cannot add ingredients to the Pantry", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("No such user associated with UID", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/pantry/removeFromPantry")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> removeFromPantry(@RequestParam String UID, @RequestParam String ingredientName) {
        User user = userRepository.findByUID(UID);

        if (user != null) { //check to make sure user exists
            if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
                //delete function can handle nonexistant Ingredients, returns boolean
                if (user.getUserPantry().deleteIngredientByName(ingredientName)) { //returns true
                    return new ResponseEntity<>(null, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND); //ingredient not found
                }
            }
            else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
                return new ResponseEntity<>("Child user cannot remove ingredients from the Pantry.", HttpStatus.FORBIDDEN);
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
        User user = userRepository.findByUID(UID);

        if(user != null){
            if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
                if (user.getUserPantry().hasIngredient(ingredientName)) {
                    return new ResponseEntity<>(user.getUserPantry().getQuantity(ingredientName), HttpStatus.OK);
                }
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
            }
            else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
                if (user.getParentUser().getUserPantry().hasIngredient(ingredientName)) {
                    return new ResponseEntity<>(user.getParentUser().getUserPantry().getQuantity(ingredientName), HttpStatus.OK);
                }
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found
    }

    @PutMapping(path = "/pantry/setQuantity")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> setQuantity(@RequestParam String UID, @RequestParam String ingredientName, @RequestParam int quantity){
        User user = userRepository.findByUID(UID);

        if(user != null){
            if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
                if (user.getUserPantry().hasIngredient(ingredientName)) {
                    user.getUserPantry().getIngredientByName(ingredientName).setQuantity(quantity);
                    return new ResponseEntity<>(pantryRepository.findByUID(UID).getQuantity(ingredientName), HttpStatus.OK);
                }
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
            }
            else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
                return new ResponseEntity<>("Child user cannot set quantity.", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found
    }

    //get and put for quantity Type

    @GetMapping(path = "/pantry/getQuantityType")
    @ResponseBody
    public ResponseEntity<Object> getQuantityType(@RequestParam String UID, @RequestParam String ingredientName){
        User user = userRepository.findByUID(UID);

        if(user != null) {
            if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
                if (user.getUserPantry().hasIngredient(ingredientName)) {
                    return new ResponseEntity<>(user.getUserPantry().getQuantityType(ingredientName), HttpStatus.OK);
                }
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
            }
            else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
                if (user.getParentUser().getUserPantry().hasIngredient(ingredientName)) {
                    return new ResponseEntity<>(user.getParentUser().getUserPantry().getQuantityType(ingredientName), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND); //user not found
    }

    @PutMapping(path = "/pantry/setQuantityType")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> setQuantityType(@RequestParam String UID, @RequestParam String ingredientName, @RequestParam String quantityType){
        User user = userRepository.findByUID(UID);

        if(user != null) {
            if (user.getAccessLevel().equals(User.AccessLevel.PARENT)) {
                if (user.getUserPantry().hasIngredient(ingredientName)) {
                    user.getUserPantry().setQuantityType(ingredientName, quantityType);
                    return new ResponseEntity<>(user.getUserPantry().getQuantityType(ingredientName), HttpStatus.OK);
                }
                return new ResponseEntity<>("no such ingredient", HttpStatus.NOT_FOUND);
            }
            else if (user.getAccessLevel().equals(User.AccessLevel.CHILD)) {
                return new ResponseEntity<>("Child user cannot set quantity type.", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("no such user", HttpStatus.NOT_FOUND);
    }


}
