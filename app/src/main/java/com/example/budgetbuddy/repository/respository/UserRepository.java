package com.example.budgetbuddy.repository.respository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetbuddy.data.Result;
import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.UserDao;
import com.example.budgetbuddy.ui.signup.SignUpState;
import com.example.budgetbuddy.utils.PasswordHelper;

import static android.content.ContentValues.TAG;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
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

    public LiveData<SignUpState> addNewUser(String username, String email, String password) {
        MutableLiveData<SignUpState> result =  new MutableLiveData<>();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                if(userDao.getUserByEmail(email) != null) {
                    result.postValue(SignUpState.DUPLICATE);
                      return;
                }
                User user = new User(username,email,password);
                userDao.insert(user);
                result.postValue(SignUpState.SUCCESS);
            } catch (Exception e) {
                Log.e(TAG, "addNewUser: ", e);
                result.postValue(SignUpState.ERROR);
            }
        });
        return result;
    }
}

