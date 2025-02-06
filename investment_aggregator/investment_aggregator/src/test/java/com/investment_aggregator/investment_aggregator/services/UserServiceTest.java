package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.controllers.dto.CreateUserDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.UpdateUserDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all users successfully")
        void findAllSuccess(){
            var user = new User(UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null);
            var userList = List.of(user);

            when(repository.findAll()).thenReturn(userList);

            var output = service.findAllUsers();

            assertNotNull(output);
            assertEquals(userList.size(), output.size());

        }
    }
    @Nested
    class delete{

        @Test
        @DisplayName("Should delete user successfuly when it exists")
        void deleteSuccess(){

            var uuid = UUID.randomUUID();

            try (MockedStatic<ConvertUUID> mockedStatic = mockStatic(ConvertUUID.class)) {
                mockedStatic.when(() -> ConvertUUID.fromHexStringToUUID(uuid.toString()))
                        .thenReturn(uuid);

                when(repository.existsById(uuidArgumentCaptor.capture())).thenReturn(true);

                doNothing()
                        .when(repository)
                        .deleteById(uuidArgumentCaptor.capture());

                service.deleteUser(uuid.toString());

                var idList = uuidArgumentCaptor.getAllValues();
                assertEquals(uuid, idList.get(0));
                assertEquals(uuid, idList.get(1));

                verify(repository, times(1)).existsById(idList.get(0));
                verify(repository, times(1)).deleteById(idList.get(1));

            }

        }
        @Test
        @DisplayName("Should delete user successfuly when it exists")
        void deleteFailure() {

            var uuid = UUID.randomUUID();

            try (MockedStatic<ConvertUUID> mockedStatic = mockStatic(ConvertUUID.class)) {
                mockedStatic.when(() -> ConvertUUID.fromHexStringToUUID(uuid.toString()))
                        .thenReturn(uuid);

                when(repository.existsById(uuidArgumentCaptor.capture())).thenReturn(false);

                service.deleteUser(uuid.toString());


                assertEquals(uuid, uuidArgumentCaptor.getValue());


                verify(repository, times(1)).existsById(uuidArgumentCaptor.getValue());
                verify(repository, times(0)).deleteById(any());

            }
        }
    }
    @Nested
    class update {

        @Test
        @DisplayName("Should update a user when it exists and when DTO is filled")
        void updateSuccess(){

            UpdateUserDTO dto = new UpdateUserDTO("novoEmail", "novaSenha");

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
                when(repository.save(userArgumentCaptor.capture())).thenReturn(user);

                service.updateUser(user.getId().toString(), dto);

                assertEquals(uuidArgumentCaptor.getValue(), user.getId());

                var userCaptured = userArgumentCaptor.getValue();
                assertEquals(userCaptured.getEmail(), dto.email());
                assertEquals(userCaptured.getPassword(), dto.password());

                verify(repository, times(1)).findById(uuidArgumentCaptor.getValue());
                verify(repository, times(1)).save(userArgumentCaptor.getValue());
            }
        }
        @Test
        @DisplayName("Should not update a user when it doesn't exists")
        void updateFailure(){
            UpdateUserDTO dto = new UpdateUserDTO("novoEmail", "novaSenha");

            var user = new User(UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null);

            try (MockedStatic<ConvertUUID> mockedStatic = mockStatic(ConvertUUID.class)) {
                mockedStatic.when(() -> ConvertUUID.fromHexStringToUUID(user.getId().toString()))
                        .thenReturn(user.getId());

                when(repository.findById(uuidArgumentCaptor.capture())).thenReturn(Optional.empty());

                service.updateUser(user.getId().toString(), dto);

                assertEquals(user.getId(), uuidArgumentCaptor.getValue());

                verify(repository, times(1)).findById(uuidArgumentCaptor.getValue());
                verify(repository, times(0)).save(any());

            }
        }


    }







}