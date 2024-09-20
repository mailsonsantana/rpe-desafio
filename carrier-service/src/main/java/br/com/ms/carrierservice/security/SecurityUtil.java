package br.com.ms.carrierservice.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility security context methods
 */

public abstract class SecurityUtil {

    private SecurityUtil() {}
    public static JwtPrincipal getCurrentJwtPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null
            && authentication.getPrincipal() instanceof JwtPrincipal jwtPrincipal) {
            return jwtPrincipal;
        }
        throw new AuthenticationCredentialsNotFoundException("Para acessar este recurso é necessário um token de autorização");
    }
}
