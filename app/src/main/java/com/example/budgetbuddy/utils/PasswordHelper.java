package com.example.budgetbuddy.utils;

import android.util.Log;

import at.favre.lib.crypto.bcrypt.BCrypt;

import static android.content.ContentValues.TAG;

public class PasswordHelper {
    public static String hashedPassword(String password){
        var hashed = BCrypt.withDefaults().hashToString(12,password.toCharArray());
        Log.i(TAG, "hashedPassword: plain -> "+password + " hashed -> " +hashed + " verified -> " + BCrypt.verifyer().verify(password.toCharArray(),hashed));
        return BCrypt.withDefaults().hashToString(12,password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        var res = BCrypt.verifyer().verify(password.toCharArray(),hashedPassword);
        Log.i(TAG, "verifyPassword: " + res.formatErrorMessage);
        return BCrypt.verifyer().verify(password.toCharArray(),hashedPassword).verified;
    }

}
