package com.sanalab.sijiusu.core.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashEncoder {
    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    public String encode(String rawPassword) {
        return bCrypt.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return bCrypt.matches(rawPassword, hashedPassword);
    }
}
