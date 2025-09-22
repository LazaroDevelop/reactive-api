package net.developer.space.reactive_api.services;

import com.mongodb.internal.connection.Server;
import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.dto.AuthRequest;
import net.developer.space.reactive_api.dto.AuthResponse;
import net.developer.space.reactive_api.dto.OTPResponse;
import net.developer.space.reactive_api.entity.User;
import net.developer.space.reactive_api.utils.JwtUtil;
import net.developer.space.reactive_api.utils.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationHandler implements IAuthenticationHandler {

    private String secret;
    private final TokenProvider tokenProvider;
    private final IUserService userService;
    private final IEmailVerificationService verificationService;
    private final ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<ServerResponse> login(ServerRequest request) {
        return request
                .bodyToMono(AuthRequest.class)
                .flatMap(auth -> userService.findByUsername(auth.getUsername())
                        .<ResponseEntity<AuthResponse>>handle((user, sink) -> {
                            if (user.getPassword().equals(auth.getPassword())) {
                                this.secret = verificationService.generateVerificationCode();
                                verificationService.sendVerificationCode(auth.getUsername(), secret);
                                sink.next(ResponseEntity.ok(new AuthResponse(tokenProvider.generateToken(auth.getUsername()))));
                            } else {
                                sink.error(new BadCredentialsException("Invalid username or password"));
                            }
                        })
                        .flatMap(res -> ServerResponse.ok().bodyValue(res))
                        .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password"))));
    }

    @Override
    public Mono<ServerResponse> signup(ServerRequest request) {
        return request
                .bodyToMono(User.class)
                .flatMap(user -> ServerResponse.ok().body(userService.storeUser(user), User.class))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    @Override
    public Mono<ServerResponse> verifyOtp(ServerRequest request){
        return request.bodyToMono(OTPResponse.class)
                .flatMap(otp -> {
                    boolean result = verificationService.verifyCode(otp.getEmail(), otp.getOtp(), secret);

                    return result ? ServerResponse.ok().bodyValue("User authenticated successfully") : Mono.error(new BadCredentialsException("Invalid or expired OTP"));
                });
    }

    @Override
    public Mono<ServerResponse> protectedEndpoint(ServerRequest request) {
        return ServerResponse.ok().bodyValue("This is protected endpoint");
    }
}
