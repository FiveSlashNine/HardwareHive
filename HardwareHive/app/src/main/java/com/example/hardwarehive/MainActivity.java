package com.example.hardwarehive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.hardwarehive.API.Retrofit2Client;
import com.example.hardwarehive.API.UserService;
import com.example.hardwarehive.Admin.CategoryPicker.AdminCategoryPickerFragment;
import com.example.hardwarehive.LoginOrRegister.LoginFragment;
import com.example.hardwarehive.User.CategoryPicker.CategoryPickerFragment;
import com.example.hardwarehive.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user==null){
            switchFragment(new LoginFragment());
        } else {
            try {
                String userEmail = user.getEmail();
                Call<User> getUserCall = Retrofit2Client.createService(UserService.class).getUserByEmail(userEmail);
                getUserCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        User tempUser = response.body();
                        if(tempUser!=null){
                            if(tempUser.isAdmin()){
                                switchFragment(new AdminCategoryPickerFragment());
                            } else {
                                switchFragment(new CategoryPickerFragment());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        switchFragment(new LoginFragment());
                    }
                });
            } catch(Exception ignored) {}

        }
    }

    public void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }
}