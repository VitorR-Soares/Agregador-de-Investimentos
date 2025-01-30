package com.investment_aggregator.investment_aggregator.controllers;

import com.investment_aggregator.investment_aggregator.controllers.dto.AccountResponseDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.CreateAccountDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.CreateUserDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.UpdateUserDTO;
import com.investment_aggregator.investment_aggregator.entities.User;
import com.investment_aggregator.investment_aggregator.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO){

        var newUser = userService.createUser(createUserDTO);

        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/findById/{userId}")
    public ResponseEntity<User> findUser(@PathVariable("userId") String userId){

        var userFound = userService.findUserById(userId);

        if (userFound.isPresent()){
            return ResponseEntity.ok(userFound.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> findAllUsers(){

        var usersFound = userService.findAllUsers();

        return ResponseEntity.ok(usersFound);

    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDTO updateUserDTO){

        userService.updateUser(userId, updateUserDTO);

        return ResponseEntity.noContent().build();

    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId){

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();

    }
    @PostMapping("/account/create/{userId}")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                              @RequestBody CreateAccountDTO createAccountDTO){

        userService.createAccount(userId, createAccountDTO);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/account/list/{userId}")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts(@PathVariable("userId") String userId){

        var accountsList = userService.listUserAccount(userId);

        return ResponseEntity.ok(accountsList);

    }




}
