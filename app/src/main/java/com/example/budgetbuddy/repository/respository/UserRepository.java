package com.example.budgetbuddy.repository.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetbuddy.data.Result;
import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.UserDao;
import com.example.budgetbuddy.utils.PasswordHelper;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public LiveData<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public LiveData<Result<User>> login(String email, String password) {
        return Transformations.map(userDao.findByEmail(email), user -> {
            if (user == null) {
                return new Result.Error(new RuntimeException("User not found"));
            }
            if (!PasswordHelper.verifyPassword(password, user.password)) {
                return new Result.Error(new RuntimeException("Invalid Username or password"));
            }
            return new Result.Success<User>(user);
        });
    }

    public void insert(MutableLiveData<Long> userId, User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            var generatedId =  userDao.insert(user);
            userId.postValue(generatedId);
        });
    }
}

