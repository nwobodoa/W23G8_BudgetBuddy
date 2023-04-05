package com.example.budgetbuddy.data;

import android.content.Context;
import android.util.Log;

import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.dao.UserDao;
import com.example.budgetbuddy.repository.respository.UserRepository;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.example.budgetbuddy.utils.PasswordHelper;

import java.io.IOException;
import java.util.Optional;

import static android.content.ContentValues.TAG;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

public class LoginDataSource {

//    public Result<User> login(String email, String password) {
//
//
//
//
//        try {
//            // TODO: handle loggedInUser authentication
//             Optional<User> optionalUser = Optional.ofNullable(userDao.findByEmail(email));
//             if (optionalUser.isEmpty()) {
//                 return  new Result.Error(new RuntimeException("User not found"));
//             }
//             User user = optionalUser.get();
//
//             if(!PasswordHelper.verifyPassword(password,user.password)) {
//                 return new Result.Error(new RuntimeException("Invalid Username or password"));
//             }
//            return new Result.Success<User>(user);
//        } catch (Exception e) {
//            Log.i("Login Failure","Login failed", e);
//            return new Result.Error(new IOException("Error logging in", e));
//        }
//    }

    public void logout() {
        // TODO: revoke authentication
    }
}
