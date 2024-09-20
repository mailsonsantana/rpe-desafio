package br.com.ms.carrierservice.security;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode(of = "user", callSuper = false)
public class JwtAuthentication extends AbstractAuthenticationToken {
	private final JwtPrincipal user;

    public JwtAuthentication(JwtPrincipal user) {
        super(user.getAuthorities());
        this.user = user;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return user.getJwtToken();
    }

    @Override
    public UserDetails getPrincipal() {
        return user;
    }

}
