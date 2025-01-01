package org.example.expert.infrastructure.config;

import org.example.expert.infrastructure.encoder.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PasswordEncoderConfig {

    @Primary
    @Bean("defaultPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }
}
