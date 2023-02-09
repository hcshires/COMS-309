package edu.iastate.cs309.hb6.FoodTime.FoodTime.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @Autowired
    UserRepository userDB;

    @PostMapping("/users/create")
    @ResponseBody
    //We can return an HTTP response as well as a UID after creating the user
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (!userDB.existsByUsername(user.getUsername())) {
            //Create a user if they do not exist in the system
            user.assignUID();
            userDB.save(user);
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/users/login")
    @ResponseBody
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        User lookup = userDB.findByUsername(user.getUsername());

        if (lookup.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<Object>(lookup.getUID(), HttpStatus.OK);
        }
        else return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/delete")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> deleteUser(@RequestBody User user) {
        if (userDB.existsByUsername(user.getUsername()) && userDB.findByUsername(user.getUsername()).getPassword().equals(user.getPassword())) {
            User deletedUser = userDB.findByUsername(user.getUsername());
            userDB.deleteById(user.getUsername());
            return new ResponseEntity<Object>(deletedUser, HttpStatus.OK);
        }
        else return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
    }
}
