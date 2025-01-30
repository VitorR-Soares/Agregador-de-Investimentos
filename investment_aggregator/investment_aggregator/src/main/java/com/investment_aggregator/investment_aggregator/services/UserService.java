package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.controllers.dto.AccountResponseDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.CreateAccountDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.CreateUserDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.UpdateUserDTO;
import com.investment_aggregator.investment_aggregator.entities.Account;
import com.investment_aggregator.investment_aggregator.entities.BillingAddress;
import com.investment_aggregator.investment_aggregator.entities.User;
import com.investment_aggregator.investment_aggregator.repositories.AccountRepository;
import com.investment_aggregator.investment_aggregator.repositories.BillingAddressRepository;
import com.investment_aggregator.investment_aggregator.repositories.UserRepository;
import com.investment_aggregator.investment_aggregator.utils.ConvertUUID;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;


    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
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
    @Transactional
    public void createAccount(String id, CreateAccountDTO dto){

        var uuid = ConvertUUID.fromHexStringToUUID(id);

        var userFound = userRepository.findById(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Account account = new Account(
                dto.description(),
                userFound,
                null,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        BillingAddress billingAddress = new BillingAddress(
                accountCreated,
                dto.street(),
                dto.number()
        );

        billingAddressRepository.save(billingAddress);

    }
    @Transactional
    public List<AccountResponseDTO> listUserAccount(String id){

        var uuid = ConvertUUID.fromHexStringToUUID(id);

        var userFound = userRepository.findById(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));


        var accountsList = userFound.getAccounts()
                .stream()
                .map(account ->
                        new AccountResponseDTO(account.getId().toString(), account.getDescription()))
                .toList();

        return accountsList;
    }


}
