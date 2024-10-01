package org.taskify.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testAuthenticateUser_Success() {
        String username = "user";
        String password = "password";

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String result = authService.authenticateUser(username, password);
        assertEquals("Usuário autenticado com sucesso", result);
    }

    @Test
    public void testAuthenticateUser_Failure() {
        String username = "user";
        String password = "password";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Falha na autenticação") {});

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
                authService.authenticateUser(username, password));

        assertEquals("Falha na autenticação. Verifique suas credenciais.", exception.getMessage());
    }
}
