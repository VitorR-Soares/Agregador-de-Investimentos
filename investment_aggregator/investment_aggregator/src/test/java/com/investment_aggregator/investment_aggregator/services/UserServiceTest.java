package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.controllers.dto.CreateUserDTO;
import com.investment_aggregator.investment_aggregator.entities.User;
import com.investment_aggregator.investment_aggregator.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user succesfully")
        void createUserSuccess(){

            var user = new User("username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null);

            doReturn(user).when(repository).save(userArgumentCaptor.capture());

            var dto = new CreateUserDTO("username",
                    "email@email.com",
                    "password");

            var output = service.createUser(dto);

            var argument = userArgumentCaptor.getValue();

            assertNotNull(output);

            assertEquals(dto.username(), argument.getUsername());
            assertEquals(dto.email(), argument.getEmail());
            assertEquals(dto.password(), argument.getPassword());

        }

        @Test
        @DisplayName("Should throw exception when error occours")
        void createUserFailure(){

            doThrow(new RuntimeException()).when(repository).save(any());

            var dto = new CreateUserDTO("username",
                    "email@email.com",
                    "password");

            assertThrows(RuntimeException.class, () -> service.createUser(dto));

        }
    }
    @Nested
    class findById{

        @Test
        @DisplayName("Should return a user succesfully when Optional is present")
        void findUserByIdOptionalPresent(){

        }
        @Test
        @DisplayName("Should return successfuly when optional is empty")
        void findUserByIdOptionalEmpty(){

        }


    }






}