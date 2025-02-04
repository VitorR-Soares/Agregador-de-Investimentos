package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.controllers.dto.CreateUserDTO;
import com.investment_aggregator.investment_aggregator.entities.User;
import com.investment_aggregator.investment_aggregator.repositories.UserRepository;
import com.investment_aggregator.investment_aggregator.utils.ConvertUUID;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    ConvertUUID convertUUID;

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
        @DisplayName("Should return a user successfully when Optional is present")
        void findUserByIdOptionalPresent(){
            var user = new User(UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null);

            try (MockedStatic<ConvertUUID> mockedStatic = mockStatic(ConvertUUID.class)) {
                mockedStatic.when(() -> ConvertUUID.fromHexStringToUUID(user.getId().toString()))
                        .thenReturn(user.getId());

                when(repository.findById(uuidArgumentCaptor.capture())).thenReturn(Optional.of(user));

                var output = service.findUserById(user.getId().toString());

                // Verificando a presença do retorno
                assertTrue(output.isPresent());
                // Verificando se o retorno é o esperado
                assertEquals(user, output.get());
                // Verificando se o valor passado no metodo do service convertido para UUID e o mesmo que chega no repository
                assertEquals(user.getId(), uuidArgumentCaptor.getValue());
            }
        }
        @Test
        @DisplayName("Should return successfuly when optional is empty")
        void findUserByIdOptionalEmpty(){

            var uuid = UUID.randomUUID();

            try (MockedStatic<ConvertUUID> mockedStatic = mockStatic(ConvertUUID.class)) {
                mockedStatic.when(() -> ConvertUUID.fromHexStringToUUID(uuid.toString()))
                        .thenReturn(uuid);

                when(repository.findById(uuidArgumentCaptor.capture())).thenReturn(Optional.empty());

                var output = service.findUserById(uuid.toString());

                assertTrue(output.isEmpty());
                assertEquals(uuid, uuidArgumentCaptor.getValue());

            }
        }
    }







}