package org.taskify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    public String authenticateUser(String usernameOrEmail, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usernameOrEmail, password)
            );

            if (authentication.isAuthenticated()) {
                logger.info("Usuário {} autenticado com sucesso.", usernameOrEmail);
                return "Usuário autenticado com sucesso";
            } else {
                logger.error("Falha ao autenticar usuário '{}'.", usernameOrEmail);
                throw new RuntimeException("Falha na autenticação. Verifique suas credenciais.");
            }
        } catch (AuthenticationException e) {
            logger.error("Falha ao autenticar usuário '{}': {}", usernameOrEmail, e.getMessage());
            throw new RuntimeException("Falha na autenticação. Verifique suas credenciais.");
        }
    }
}
