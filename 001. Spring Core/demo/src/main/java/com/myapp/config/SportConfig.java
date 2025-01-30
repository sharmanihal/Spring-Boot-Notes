package com.myapp.config;

import com.myapp.util.Coach;
import com.myapp.util.TennisCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean
    public Coach tennisCoach() {
        return new TennisCoach();
    }
}
