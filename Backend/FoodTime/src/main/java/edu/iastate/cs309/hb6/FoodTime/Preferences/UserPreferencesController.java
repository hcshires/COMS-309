package edu.iastate.cs309.hb6.FoodTime.Preferences;

import edu.iastate.cs309.hb6.FoodTime.Login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPreferencesController {

    @Autowired
    UserRepository userDB;

    @PutMapping("/preferences/update")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> updatePreferences(@RequestBody UserPreferences prefs) {
        if (userDB.existsById(prefs.getUID())) {
            UserPreferences prefsToUpdate = userDB.findByUID(prefs.getUID()).getUserPreferences();
            //User preferences update without a .save() since method is transactional
            prefsToUpdate.update(prefs);
            return new ResponseEntity<>(prefsToUpdate, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
