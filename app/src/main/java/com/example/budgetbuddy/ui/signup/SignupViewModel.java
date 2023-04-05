package com.example.budgetbuddy.ui.signup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.respository.UserRepository;

import java.util.Objects;

public class SignupViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    public SignupViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }
    public LiveData<Boolean> userExists(String email) {
        return Transformations.map(userRepository.findUserByEmail(email), Objects::nonNull);
    }

    public void addNewUser(MutableLiveData<Long> userId, User user) {
      userRepository.insert(userId,user);
    }
}
