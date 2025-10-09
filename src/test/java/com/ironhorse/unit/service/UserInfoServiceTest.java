package com.ironhorse.unit.service;

import com.ironhorse.dto.UserInfoCreateDto;
import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import com.ironhorse.dto.googleGeoCode.LocationDto;
import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.exception.UserInfoNotFoundException;
import com.ironhorse.model.User;
import com.ironhorse.model.UserInfo;
import com.ironhorse.repository.UserInfoRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.GeocodeService;
import com.ironhorse.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {

    @InjectMocks
    public UserInfoServiceImpl userInfoService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private AuthenticatedService authenticatedService;

    @Mock
    private GeocodeService geocodeService;

    private User mockUser;

    @BeforeEach
    public void setUp(){
        this.mockUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@email.com")
                .phone("51 9999-9999")
                .created_at(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should be able to save a User Info successfully")
    public void shouldBeAbleToSaveUserInfoSuccessfully(){
        Long userId = this.mockUser.getId();

        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(this.mockUser));

        UserInfoCreateDto requestUserInfo = this.buildValidUserInfoCreateDto();

        String address = String.format("%s, %s, %s, %s",
                requestUserInfo.streetAddress(),
                requestUserInfo.streetName(),
                requestUserInfo.city(),
                requestUserInfo.state());
        LocationDto mockLocation = new LocationDto(51.4036, -0.5076);
        when(this.geocodeService.getLatitudeAndLongitude(address)).thenReturn(mockLocation);

        UserInfo savedUserInfo = UserInfo.builder()
                .cpf("123.456.789-00")
                .streetAddress("Rua")
                .streetName(" Gonçalo de Carvalho")
                .streetNumber(4L)
                .acceptComunication(true)
                .isTermsUser(true)
                .isRegularized(true)
                .isRealInformation(true)
                .district("Independência")
                .zipcode("123456-777")
                .city("Porto Alegre")
                .state("RS")
                .driverLicense("1111111111")
                .build();

        when(this.userInfoRepository.save(any(UserInfo.class))).thenReturn(savedUserInfo);

        UserInfoResponseDto response = this.userInfoService.save(requestUserInfo);

        ArgumentCaptor<UserInfo> userInfoCaptor = ArgumentCaptor.forClass(UserInfo.class);
        verify(this.userInfoRepository).save(userInfoCaptor.capture());
        UserInfo userInfoCaptorSaved = userInfoCaptor.getValue();

        verify(this.authenticatedService).getCurrentUserId();
        verify(this.geocodeService).getLatitudeAndLongitude(address);
        verify(this.userRepository).findById(userId);

        assertNotNull(userInfoCaptorSaved.getUser());
        assertEquals(this.mockUser, userInfoCaptorSaved.getUser());
        assertEquals(savedUserInfo.getCpf(), response.cpf());
        assertEquals(mockLocation.lat(), userInfoCaptorSaved.getLatitude());
        assertEquals(mockLocation.lng(), userInfoCaptorSaved.getLongitude());
    }

    @Test
    @DisplayName("Should not be able to find UserInfo that not exists")
    public void shouldNotBeAbleToFindUserInfoThatNotExists(){
        Long userId = this.mockUser.getId();
        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(this.mockUser));
        when(this.userInfoRepository.findByUserId(userId)).thenReturn(Optional.empty());

        UserInfoNotFoundException exception = assertThrows(UserInfoNotFoundException.class,
                () -> this.userInfoService.findByUserId());

        assertEquals("Informações não encontradas", exception.getMessage());
    }

    @Test
    @DisplayName("Should not be able to get User different than logged in")
    public void shouldNotBeAbleToGetUserDifferentThanLoggedIn(){
        Long userId = this.mockUser.getId();
        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(this.mockUser));

        User differentUser = User.builder()
                .id(9L)
                .name("Different User Name")
                .email("different@email.com")
                .phone("51 98888-8888")
                .created_at(LocalDateTime.now())
                .build();

        UserInfo differentUserInfo = UserInfo.builder()
                .id(9L)
                .user(differentUser)
                .cpf("987.654.321-00")
                .streetAddress("Rua")
                .streetName(" São João")
                .streetNumber(5L)
                .acceptComunication(true)
                .isTermsUser(true)
                .isRegularized(true)
                .isRealInformation(true)
                .district("Rio Branco")
                .zipcode("654321-000")
                .city("São Leopoldo")
                .state("RS")
                .driverLicense("2222222222")
                .created_at(LocalDateTime.now())
                .build();

        when(this.userInfoRepository.findByUserId(userId)).thenReturn(Optional.of(differentUserInfo));
        ForbiddenAccessException exception = assertThrows(ForbiddenAccessException.class,
                ()-> this.userInfoService.findByUserId());

        assertEquals("Você não pode acessar este recurso", exception.getMessage());
    }

    @Test
    @DisplayName("Should be able to find by UserId")
    public void shouldBeAbleToFindByUserId(){
        Long userId = this.mockUser.getId();
        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(this.mockUser));

        UserInfo userInfo = UserInfo.builder()
                .id(9L)
                .user(this.mockUser)
                .cpf("987.654.321-00")
                .streetAddress("Rua")
                .streetName(" São João")
                .streetNumber(5L)
                .acceptComunication(true)
                .isTermsUser(true)
                .isRegularized(true)
                .isRealInformation(true)
                .district("Rio Branco")
                .zipcode("654321-000")
                .city("São Leopoldo")
                .state("RS")
                .driverLicense("2222222222")
                .created_at(LocalDateTime.now())
                .build();

        when(this.userInfoRepository.findByUserId(userId)).thenReturn(Optional.of(userInfo));

        UserInfoResponseDto saved = this.userInfoService.findByUserId();

        assertEquals(saved.cpf(), userInfo.getCpf());
        assertSame(this.mockUser, userInfo.getUser());
    }

    @Test
    @DisplayName("Should be able to delete a User Info by User Id")
    public void shouldBeAbleToDeleteAUserInfoByUserId(){
        Long userId = this.mockUser.getId();

        when(this.userInfoRepository.deleteByUserId(userId)).thenReturn(1L);
        doNothing().when(this.userInfoRepository).flush();

        Long rowsAffected = this.userInfoService.deleteByUserId(userId);

        verify(this.userInfoRepository).flush();
        assertEquals(1L, rowsAffected);
    }

    @Test
    @DisplayName("Should not be able to delete User Info not exists")
    public void shouldNotBeAbleToDeleteUserInfoNotExists(){
        when(this.userInfoRepository.deleteByUserId(9L)).thenReturn(0L);

        UserInfoNotFoundException exception = assertThrows(UserInfoNotFoundException.class,
                () -> this.userInfoService.deleteByUserId(9L));

        assertEquals("Informações do usuário não encontradas", exception.getMessage());
    }

    @Test
    @DisplayName("Should be able to update a User Info successfully")
    public void shouldBeAbleToUpdateAUserInfoSuccessfully(){
        Long userId = this.mockUser.getId();
        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);

        UserInfo existingUserInfo = UserInfo.builder()
                .id(9L)
                .user(this.mockUser)
                .cpf("987.654.321-00")
                .streetAddress("Rua")
                .streetName(" São João")
                .streetNumber(5L)
                .acceptComunication(true)
                .isTermsUser(true)
                .isRegularized(true)
                .isRealInformation(true)
                .district("Rio Branco")
                .zipcode("654321-000")
                .city("São Leopoldo")
                .state("RS")
                .driverLicense("2222222222")
                .created_at(LocalDateTime.now())
                .build();

        when(this.userInfoRepository.findById(userId)).thenReturn(Optional.of(existingUserInfo));

        UserInfoDto updateUserInfo = this.buildValidUserInfoDto();

        String address = String.format("%s, %s, %s, %s",
                updateUserInfo.streetAddress(),
                updateUserInfo.streetName(),
                updateUserInfo.city(),
                updateUserInfo.state());
        LocationDto mockLocation = new LocationDto(51.4036, -0.5076);
        when(this.geocodeService.getLatitudeAndLongitude(address)).thenReturn(mockLocation);

        doNothing().when(this.userInfoRepository).flush();

        UserInfoResponseDto response = this.userInfoService.update(updateUserInfo);

        verify(this.geocodeService).getLatitudeAndLongitude(address);
        verify(this.userInfoRepository).flush();

        assertNotNull(response);
        assertEquals(updateUserInfo.streetName(), response.streetName());
        assertEquals(updateUserInfo.city(), response.city());
        assertEquals(updateUserInfo.driverLicense(), response.driverLicense());
    }

    @Test
    @DisplayName("Should not be able to update User Info thats not exists")
    public void shouldNoTBeAbleToUpdateUserInfoThatsNotExists(){
        Long userId = this.mockUser.getId();
        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.userInfoRepository.findById(userId)).thenReturn(Optional.empty());

        UserInfoDto userInfoRequest = this.buildValidUserInfoDto();

        UserInfoNotFoundException exception = assertThrows(UserInfoNotFoundException.class,
                () -> this.userInfoService.update(userInfoRequest));

        assertEquals("Informações do usuário não encontradas", exception.getMessage());
    }

    private UserInfoCreateDto buildValidUserInfoCreateDto() {
        return new UserInfoCreateDto(
                "123.456.789-00", "Rua", " Gonçalo de Carvalho",
                4L, true, true, true, true,
                "Independência", "123456-777", "Porto Alegre", "RS", "1111111111"
        );
    }

    private UserInfoDto buildValidUserInfoDto(){
        return new UserInfoDto(
                "123.456.789-00", "Rua", " Gonçalo de Carvalho",
                4L,"Independência", "123456-777",
                "Porto Alegre", "RS", "1111111111"
        );
    }

}
