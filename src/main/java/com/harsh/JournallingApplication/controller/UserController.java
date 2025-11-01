package com.harsh.JournallingApplication.controller;
import com.harsh.JournallingApplication.entity.User;
import com.harsh.JournallingApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for managing users.
 *
 * Base URL: /user
 *
 * Features:
 * - Retrieve all users
 * - Create a new user
 * - Update existing user details
 *
 * ⚠️ Security Note: Exposing full user details (like passwords)
 *   in real-world apps is dangerous. This code assumes
 *   passwords are already hashed (via service layer or entity listeners).
 */
@RestController
@RequestMapping("/user")
public class UserController {
    // Service to handle user persistence and retrieval
    @Autowired
    private UserService userService;

    /**
     * Fetch all users in the system.
     *
     * @return List<User> - A list of all registered users
     *         (HTTP 200 OK always returned, even if list is empty).
     */
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    /**
     * Create a new user and persist it in the database.
     *
     * @param user User object from request body.
     *             Should contain valid username + password.
     *
     * @return void - No response entity returned.
     *
     * ⚠️ Note: In production, response should ideally return 201 CREATED
     *   with new resource details or a location header.
     */
    @PostMapping("/register")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

    /**
     * Update the details of an existing user identified by username.
     *
     * @param user Incoming user object from request body (may contain new username/password).
     * @param userName Path parameter identifying which user to update.
     *
     * @return 204 NO_CONTENT if update successful
     *         200 OK (with updated user) could be better to return in real-world scenarios
     *         404 NOT_FOUND could be returned if user doesn’t exist (not implemented here).
     */
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
        User userInDb = userService.findByUsername(userName);
        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    @PostMapping("/login")
//    public String login(@RequestBody User user){
//        String result = userService.verify(user);
//        return result;
//    }

}