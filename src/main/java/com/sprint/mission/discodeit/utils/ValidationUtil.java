package com.sprint.mission.discodeit.utils;

public class ValidationUtil {

    public boolean isEmailValid(String email) {
        return email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean isPasswordValid(String password) {
        //길이는 8자리 이상, 영어 대소문자, 숫자, 특수문자 포함
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    }

}
