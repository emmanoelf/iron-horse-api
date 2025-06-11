package com.ironhorse.service;

import com.ironhorse.dto.EmailDto;
import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.model.User;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    private User mockUser;

    @BeforeEach
    public void setUp(){
        this.mockUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@test.com")
                .phone("51-99999-9999")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();
    }

    @DisplayName("Should be able to save a user successfully")
    @Test
    public void shouldBeAbleToSaveUser(){
        String rawPassword = "password";
        String hashedPassword = "5f4dcc3b5aa765d61d8327deb882cf99";
        UserDto userDto = new UserDto("John Doe", "johndoe@test.com", rawPassword, "51-99999-9999");

        when(this.passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);

        User savedUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@test.com")
                .password(hashedPassword)
                .phone("51-99999-9999")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        when(this.userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto response = this.userService.save(userDto);

        verify(this.passwordEncoder).encode(rawPassword);
        verify(this.userRepository).save(any(User.class));
        verify(this.emailService).sendEmail(any(EmailDto.class));

        assertEquals(savedUser.getId(), response.id());
        assertEquals(savedUser.getEmail(), response.email());
    }

    @DisplayName("Should be able to delete a user successfully")
    @Test
    public void shouldBeAbleToDeleteAUser() {
        Long id = 1L;
        when(this.userRepository.deleteUserById(id)).thenReturn(1L);

        Long response = this.userService.deleteById(id);

        verify(this.userRepository).flush();
        assertEquals(1L, response);
    }

    @DisplayName("Should not be able to delete an user was not found")
    @Test
    public void shouldNotBeAbleToDeleteAUser() {
        Long id = 3L;
        when(this.userRepository.deleteUserById(id)).thenReturn(0L);

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> this.userService.deleteById(id));

        assertEquals("Entidade não encontrada", exception.getMessage());
    }

    @DisplayName("Should be able to get a user by id")
    @Test
    public void shouldBeAbleToGetAUserById(){
        Long id = 1L;
        when(this.userRepository.findById(id)).thenReturn(Optional.of(this.mockUser));

        UserResponseDto userResponse = this.userService.findById(1L);

        assertEquals(userResponse.id(), this.mockUser.getId());
    }

    @DisplayName("Should not be able to get a user was not exists")
    @Test
    public void shouldBeThrowExceptionWhenUserNotFound(){
        when(this.userRepository.findById(1L)).thenReturn(Optional.empty());
        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> this.userService.findById(1L));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @DisplayName("Should not be able to update a user was not exists")
    @Test
    public void shouldThrowExceptionWhenUpdateUserNotFound(){
        Long id = 3L;
        UserDto updatedUser = new UserDto("Updated Name", "updated_mail@test.com", "updated_password", "51-88888-8888");

        when(this.userRepository.findById(id)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class,
                () -> this.userService.update(id, updatedUser));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @DisplayName("Should be able to update a user successfully")
    @Test
    public void shouldBeAbleToUpdateAUser(){
        UserDto updatedUser = new UserDto("Updated Name", "updated_mail@test.com", "updated_password", "51-88888-8888");

        User getUser = User.builder()
                .id(this.mockUser.getId())
                .name(this.mockUser.getName())
                .email(this.mockUser.getEmail())
                .password(this.mockUser.getPassword())
                .phone(this.mockUser.getPhone())
                .created_at(this.mockUser.getCreated_at())
                .updated_at(this.mockUser.getUpdated_at())
                .build();

        when(this.userRepository.findById(this.mockUser.getId())).thenReturn(Optional.of(getUser));
        when(this.passwordEncoder.encode(updatedUser.password())).thenReturn("updated_password");
        when(this.userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = this.userService.update(this.mockUser.getId(), updatedUser);

        verify(this.userRepository).findById(this.mockUser.getId());
        verify(this.passwordEncoder).encode(updatedUser.password());
        verify(this.userRepository).save(any(User.class));

        assertEquals(this.mockUser.getId(), response.id());
        assertEquals("updated_mail@test.com", response.email());
    }
}
