
package com.tolls.smart_toll_system;

import com.tolls.smart_toll_system.dto.LoginRequest;
import com.tolls.smart_toll_system.dto.RegisterRequest;
import com.tolls.smart_toll_system.entity.User;
import com.tolls.smart_toll_system.entity.Wallet;
import com.tolls.smart_toll_system.repository.UserRepository;
import com.tolls.smart_toll_system.repository.WalletRepository;
import com.tolls.smart_toll_system.service.AuthService;
import com.tolls.smart_toll_system.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setName("Parth Bhardwaj");
        registerRequest.setEmail("parth@gmail.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("parth@gmail.com");
        loginRequest.setPassword("password123");

        mockUser = User.builder()
                .id(1L)
                .name("Parth Bhardwaj")
                .email("parth@gmail.com")
                .password("encodedPassword")
                .role(User.Role.USER)
                .build();
    }

    @Test
    void register_ShouldCreateUserAndWallet_WhenEmailNotExists() {
        when(userRepository.existsByEmail("parth@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(walletRepository.save(any(Wallet.class))).thenReturn(
                Wallet.builder().balance(BigDecimal.ZERO).user(mockUser).build());
        when(jwtUtil.generateToken("parth@gmail.com")).thenReturn("mockToken");

        var response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.getToken());
        assertEquals("Parth Bhardwaj", response.getName());
        assertEquals("parth@gmail.com", response.getEmail());
        assertEquals("USER", response.getRole());
        verify(userRepository, times(1)).save(any(User.class));
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail("parth@gmail.com")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.register(registerRequest));

        assertEquals("Email already registered!", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsValid() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByEmail("parth@gmail.com"))
                .thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken("parth@gmail.com")).thenReturn("mockToken");

        var response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.getToken());
        assertEquals("parth@gmail.com", response.getEmail());
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByEmail("parth@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    }
}