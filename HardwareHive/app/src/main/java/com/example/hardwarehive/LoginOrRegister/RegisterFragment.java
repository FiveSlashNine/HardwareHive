package com.example.hardwarehive.LoginOrRegister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.hardwarehive.API.Retrofit2Client;
import com.example.hardwarehive.API.UserService;
import com.example.hardwarehive.Admin.CategoryPicker.AdminCategoryPickerFragment;
import com.example.hardwarehive.MainActivity;
import com.example.hardwarehive.R;
import com.example.hardwarehive.User.CategoryPicker.CategoryPickerFragment;
import com.example.hardwarehive.model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private UserService userService;
    private View root;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        userService = Retrofit2Client.createService(UserService.class);

        TextInputLayout emailInputLayout = root.findViewById(R.id.registerEmailTextField);
        TextInputLayout usernameInputLayout = root.findViewById(R.id.registerUsernameTextField);
        TextInputLayout addressInputLayout = root.findViewById(R.id.registerAddressTextField);
        TextInputLayout passwordInputLayout = root.findViewById(R.id.registerPasswordTextField);

        root.findViewById(R.id.registerButton).setOnClickListener(v -> {
            String email = String.valueOf((Objects.requireNonNull(emailInputLayout.getEditText())).getText());
            String username = String.valueOf((Objects.requireNonNull(usernameInputLayout.getEditText())).getText());
            String address = String.valueOf((Objects.requireNonNull(addressInputLayout.getEditText())).getText());
            String password = String.valueOf((Objects.requireNonNull(passwordInputLayout.getEditText())).getText());

            if (email.isEmpty()) {
                Toast.makeText(getContext(), "Enter an email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(username.isEmpty()) {
                Toast.makeText(getContext(), "Enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            if(address.isEmpty()) {
                Toast.makeText(getContext(), "Enter an address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(getContext(), "Enter a password", Toast.LENGTH_SHORT).show();
                return;
            } else if(password.length()<6) {
                Toast.makeText(getContext(), "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "The registration was successful.", Toast.LENGTH_SHORT).show();

                    try{
                        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        SwitchCompat isAdminSwitch = root.findViewById(R.id.isAdminSwitch);
                        if(isAdminSwitch.isChecked()) {
                            User u = new User(email, username, address, 0, true, userId);
                            Call<Void> createUser = userService.createUser(u);
                            createUser.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    AdminCategoryPickerFragment adminCategoryPickerFragment = new AdminCategoryPickerFragment();
                                    ((MainActivity) requireActivity()).switchFragment(adminCategoryPickerFragment);
                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                                }
                            });
                        }
                        else {
                            User u = new User(email, username, address, 0, false, userId);
                            Call<Void> createUser = userService.createUser(u);
                            createUser.enqueue(new Callback<Void>() {

                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    CategoryPickerFragment categoryPickerFragment = new CategoryPickerFragment();
                                    ((MainActivity) requireActivity()).switchFragment(categoryPickerFragment);
                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                                }
                            });
                        }
                    }catch (Exception ignore) {}
                } else {
                    Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });

        });

        root.findViewById(R.id.moveToLoginTextView).setOnClickListener(v -> {
            LoginFragment loginFragment = new LoginFragment();
            ((MainActivity) requireActivity()).switchFragment(loginFragment);
        });

        return root;
    }
}