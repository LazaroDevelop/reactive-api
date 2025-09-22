package net.developer.space.reactive_api.configuration;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MfaConfig {

    @Bean
    public SecretGenerator secretGenerator(){
        return new DefaultSecretGenerator();
    }

    @Bean
    public QrGenerator grGenerator(){
        return new ZxingPngQrGenerator();
    }

    @Bean
    public CodeVerifier codeVerifier(){
        //Time provider
        TimeProvider timeProvider = new SystemTimeProvider();
        //Generator of code
        CodeGenerator codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA512, 6);
        //Code verifier
        DefaultCodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        codeVerifier.setTimePeriod(30);
        codeVerifier.setAllowedTimePeriodDiscrepancy(2);

        return codeVerifier;
    }
}
