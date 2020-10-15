package com.legerdy.databot.controllers;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.legerdy.databot.models.User;
import com.legerdy.databot.repositories.GuildHasUserRepository;
import com.legerdy.databot.repositories.GuildRepository;
import com.legerdy.databot.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private GuildHasUserRepository guildHasUserRepository;

    /**
     * Used to check if the API is running
     * @return ResponseEntity with json message
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getAPI() {
        return ResponseEntity.status(HttpStatus.OK).body(
            Collections.singletonMap("message", "API is running!")
        );
    }

    /**
     * Clears the user repository
     * @return ResponseEntity
     */
    @GetMapping("/reset")
    public ResponseEntity resetRepositories(){

        guildHasUserRepository.deleteAll();
        userRepository.deleteAll();
        // guildRepository.deleteAll();

        // OK
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * Creates a new user object and saves it in the database.
     * 
     * @param id
     * @param guild
     * @param username
     * @param nickname
     * @param active
     * @return ResponseEntity with the user object created.
     */
    @PostMapping("/user")
    public ResponseEntity<User> newUser(@RequestParam(name="id") final String id
                                    , @RequestParam(name="username") final String username
                                    , @RequestParam(name="active") final boolean active) {

        User newUser = new User(id, username, active);

        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e){
            // CONFLICT if user with this id already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        // CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    /**
     * Updates a users username.
     * 
     * @param user
     * @param username
     * @return ResponseEntity with the user updated.
     */
    @PatchMapping("/user")
    public ResponseEntity<User> updateUsername(@RequestParam(name="user") final String userId
                                            , @RequestParam(name="username") final String username) {
        

        Optional<User> userResponse = userRepository.findById(userId);

        if(userResponse.isPresent()){

            User user = userResponse.get();
            user.setUsername(username);

            try{
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                // BAD REQUEST
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            
            // OK
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        // NOT FOUND if no user found with given user id
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}