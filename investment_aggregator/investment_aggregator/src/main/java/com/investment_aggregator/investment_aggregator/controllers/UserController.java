package com.investment_aggregator.investment_aggregator.controllers;

import com.investment_aggregator.investment_aggregator.controllers.dto.CreateUserDTO;
import com.investment_aggregator.investment_aggregator.entities.User;
import com.investment_aggregator.investment_aggregator.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO){

        var newUser = userService.createUser(createUserDTO);

        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/findUser/{userId}")
    public ResponseEntity<User> findUser(@PathVariable("userId") String userId){

        var userFound = userService.findUserById(userId);

        if (userFound.isPresent()){
            return ResponseEntity.ok(userFound.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }




}
