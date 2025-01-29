package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.controllers.dto.CreateUserDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.UpdateUserDTO;
import com.investment_aggregator.investment_aggregator.entities.User;
import com.investment_aggregator.investment_aggregator.repositories.UserRepository;
import com.investment_aggregator.investment_aggregator.utils.ConvertUUID;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(CreateUserDTO dto){

        var newUser = new User(
                dto.username(),
                dto.email(),
                dto.password(),
                Instant.now(),
                null);


        var user = userRepository.save(newUser);

        return user;
    }

    @Transactional
    public Optional<User> findUserById(String id){

        var uuid = ConvertUUID.fromHexStringToUUID(id);

        var userFound = userRepository.findById(uuid);

        return userFound;
    }
    @Transactional
    public List<User> findAllUsers(){

        var allUsers = userRepository.findAll();

        return allUsers;

    }
    @Transactional
    public void updateUser(String id, UpdateUserDTO dto){

        var uuid = ConvertUUID.fromHexStringToUUID(id);

        var userIsPresent = userRepository.findById(uuid);

        if (userIsPresent.isPresent()){

            User updateUser = userIsPresent.get();


            if(dto.email() != null){
                updateUser.setEmail(dto.email());
            }
            if(dto.password() != null) {
                updateUser.setPassword(dto.password());
            }

            userRepository.save(updateUser);

        }

    }

    @Transactional
    public void deleteUser(String id){

        var uuid = ConvertUUID.fromHexStringToUUID(id);

        var userFound = userRepository.existsById(uuid);

        if(userFound){
            userRepository.deleteById(uuid);
        }

    }


}
