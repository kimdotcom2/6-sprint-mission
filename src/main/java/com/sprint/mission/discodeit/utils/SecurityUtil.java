package com.sprint.mission.discodeit.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    public String hashPassword(String password) {

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");

            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Failed to hash password.");
        }


    }

}
