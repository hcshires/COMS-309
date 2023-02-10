package edu.iastate.cs309.hb6.FoodTime.Login;


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
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/users/login")
    @ResponseBody
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        User lookup = userDB.findByUsername(user.getUsername());

        if (lookup.getPassword().equals(user.getPassword())) {
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
            userDB.deleteById(user.getUsername());
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
