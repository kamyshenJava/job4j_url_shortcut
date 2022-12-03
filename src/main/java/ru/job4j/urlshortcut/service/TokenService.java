package ru.job4j.urlshortcut.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateToken(Authentication authentication);
}
