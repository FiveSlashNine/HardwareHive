package com.example.hardwarehive.LoginOrRegister;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hardwarehive.API.Retrofit2Client;
import com.example.hardwarehive.API.UserService;
import com.example.hardwarehive.Admin.CategoryPicker.AdminCategoryPickerFragment;
import com.example.hardwarehive.MainActivity;
import com.example.hardwarehive.R;
import com.example.hardwarehive.User.CategoryPicker.CategoryPickerFragment;
import com.example.hardwarehive.model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private UserService userService;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        userService = Retrofit2Client.createService(UserService.class);
        mAuth = FirebaseAuth.getInstance();

        TextInputLayout emailInputLayout = root.findViewById(R.id.loginEmailTextField);
        TextInputLayout passwordInputLayout = root.findViewById(R.id.loginPasswordTextField);

        root.findViewById(R.id.loginButton).setOnClickListener(v -> {
            String email = String.valueOf((Objects.requireNonNull(emailInputLayout.getEditText())).getText());
            String password = String.valueOf((Objects.requireNonNull(passwordInputLayout.getEditText())).getText());

            if (email.isEmpty()) {
                Toast.makeText(getContext(), "Enter an email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(getContext(), "Enter a password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login was successful.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null) {
                                try {
                                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                    Call<User> getUserCall = userService.getUserById(userId);
                                    getUserCall.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                                            User tempUser = response.body();
                                            if(tempUser!=null){
                                                if(tempUser.isAdmin()){
                                                    AdminCategoryPickerFragment adminCategoryPickerFragment = new AdminCategoryPickerFragment();
                                                    ((MainActivity) requireActivity()).switchFragment(adminCategoryPickerFragment);
                                                } else {
                                                    CategoryPickerFragment categoryPickerFragment = new CategoryPickerFragment();
                                                    ((MainActivity) requireActivity()).switchFragment(categoryPickerFragment);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                                            Toast.makeText(getContext(), "Failed to retrieve user", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch(Exception ignored) {}
                            }
                        } else {
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        root.findViewById(R.id.moveToSignUpTextView).setOnClickListener(v -> {
            RegisterFragment registerFragment = new RegisterFragment();
            ((MainActivity) requireActivity()).switchFragment(registerFragment);
        });

        return root;
    }
}