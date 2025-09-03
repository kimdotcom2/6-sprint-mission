package com.sprint.mission.discodeit.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class SecurityUtil {

    public String hashPassword(String password) {

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");

            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Failed to hash password.");
        }

    }

}
