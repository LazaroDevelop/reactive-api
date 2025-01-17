package net.developer.space.reactive_api.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@EqualsAndHashCode
public class JwtToken extends AbstractAuthenticationToken {

    private final String token;
    private final UserDetails principal;

    public JwtToken(String token, UserDetails principal){
        super(principal.getAuthorities());
        this.token = token;
        this.principal = principal;
    }

    public Authentication withAuthenticated(boolean isAuthenticated){
        JwtToken clone = new JwtToken(token, principal);
        clone.setAuthenticated(isAuthenticated);
        return clone;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
