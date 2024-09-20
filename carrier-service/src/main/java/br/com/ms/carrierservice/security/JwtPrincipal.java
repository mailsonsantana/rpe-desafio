package br.com.ms.carrierservice.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class JwtPrincipal implements UserDetails {
	@Getter
    private String cpf;

    @Getter
    private String jwtToken;

    /*
     * Constructor
     */

    public JwtPrincipal(String cpf, String token) {
        this.cpf = cpf;
        this.jwtToken = token;
    }

    /*
     * Public Methods
     */

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("AUTHENTICATED"));
    }

    @Override
    public String getPassword() {
        return "token auth";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

	@Override
	public String getUsername() {
		return null;
	}
}
