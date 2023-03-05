package com.example.budgetbuddy.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHelper {
    public static String hashedPassword(String password){
        return BCrypt.withDefaults().hashToString(12,password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(),hashedPassword).verified;
    }

}
