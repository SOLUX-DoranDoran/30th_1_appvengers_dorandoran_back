package com.app.dorandoran_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
@SpringBootTest
public class EnvCheck {

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Test
    public void printEnv() {
        System.out.println("DB_PASSWORD: " + dbPassword);
        System.out.println("JWT_SECRET: " + jwtSecret);
    }
}
