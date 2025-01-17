package net.developer.space.reactive_api.utils;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider {
    String generateToken(String username);
}
