package net.developer.space.reactive_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsConfiguration {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    MapReactiveUserDetailsService reactiveUserDetailsService(PasswordEncoder passwordEncoder){
        var user = User.builder()
                .username("lazaro")
                .password(passwordEncoder.encode("admin"))
                .roles("USER")
                .build();

        return new MapReactiveUserDetailsService(user);
    }

}
