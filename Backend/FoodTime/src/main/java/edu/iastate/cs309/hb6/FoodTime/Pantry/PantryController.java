package edu.iastate.cs309.hb6.FoodTime.Pantry;

/*
    @author Blake Hardy

 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PantryController {

    @Autowired
    PantryRepository pantryRepository;

    //just quick response messages ig?

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @GetMapping(path = "/pantries")
    List<Pantry> getAllPantries(){
        return pantryRepository.findAll();
    }

    @GetMapping(path = "/pantries/{id}")
    Pantry getPantryById(@PathVariable int id){
        return pantryRepository.findById(id);
    }

    @PostMapping(path = "/pantries")
    String createPantry(@RequestBody Pantry Pantry){
        if (Pantry == null)
            return failure;
        pantryRepository.save(Pantry);
        return success;
    }

    @PutMapping(path = "/pantries/{id}")
    Pantry updatePantry(@PathVariable int id, @RequestBody Pantry request){
        Pantry pantry = pantryRepository.findById(id);
        if(pantry == null)
            return null;
        pantryRepository.save(request);
        return pantryRepository.findById(id);
    }

    @DeleteMapping(path = "/pantries/{id}")
    String deletePantry(@PathVariable int id){
        pantryRepository.deleteById(id);
        return success;
    }

    //might need to add a method to both create a new pantry object given that info plus a user object to associate it with
    //might simplify things a lot. or I could implement it as a controller ig? idk

}