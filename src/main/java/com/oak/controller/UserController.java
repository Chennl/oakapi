package com.oak.controller;

import com.oak.model.UserInfo;
import com.oak.service.UserService;
import com.oak.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value = "users",method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers()
    {
        List<UserInfo> user = userService.getAllUsers();
        if(user.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserInfo>>(user,HttpStatus.OK);
    }
    @RequestMapping(value="/user/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
        logger.info("Fetching User with id {}", id);
        UserInfo user= userService.getUserById(id);
        if(user == null){
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("UserInfo with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }

    @RequestMapping(value="/user/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?>  updateUser(@PathVariable("id") long id, @RequestBody UserInfo user) {
        logger.info("Updating User with id {}", id);
        UserInfo currentUser = userService.getUserById(id);
        if(currentUser == null){
            logger.error("Unable to update. UserInfo with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. UserInfo with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentUser.setUserName(user.getUserName());
        currentUser.setEmail(user.getEmail());
        currentUser.setEnabled(user.getEnabled());
        currentUser.setRole(user.getRole());
       // currentUser.setPassword(user.getPassword());
        currentUser.setFullName(user.getFullName());

        userService.updateUser(currentUser);
        return new ResponseEntity<UserInfo>(currentUser, HttpStatus.OK);

    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserInfo user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A UserInfo with name {} already exist", user.getUserName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getUserName() + " already exist."),HttpStatus.CONFLICT);
        }
        userService.addUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/employee/{id}").buildAndExpand(user.getUserId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    // ------------------- Delete a UserInfo-----------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);

        UserInfo user = userService.getUserById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. UserInfo with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        return new ResponseEntity<UserInfo>(HttpStatus.NO_CONTENT);
    }
}
