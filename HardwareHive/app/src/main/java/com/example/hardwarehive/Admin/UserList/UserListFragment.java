package com.example.hardwarehive.Admin.UserList;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hardwarehive.API.Retrofit2Client;
import com.example.hardwarehive.API.UserService;
import com.example.hardwarehive.Admin.CategoryPicker.AdminCategoryPickerFragment;
import com.example.hardwarehive.MainActivity;
import com.example.hardwarehive.R;
import com.example.hardwarehive.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListFragment extends Fragment implements UserListInterface {
    private View root;
    private UserListAdapter adapter;
    private List<User> userList;
    private UserService userService;

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_list, container, false);

        userService = Retrofit2Client.createService(UserService.class);

        RecyclerView recyclerView = root.findViewById(R.id.UserRecyclerView);

        userList = new ArrayList<>();
        adapter = new UserListAdapter(getContext(), userList, this);

        fetchUsers();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        goBackToCategoriesButtonInitializer();

        return root;
    }

    // Method used by the goBackButton to return to the Category Picker screen
    public void goBackToCategoriesButtonInitializer() {
        root.findViewById(R.id.goBackButton).setOnClickListener(v -> {
            AdminCategoryPickerFragment categoryPickerFragment = new AdminCategoryPickerFragment();
            ((MainActivity) requireActivity()).switchFragment(categoryPickerFragment);
        });
    }

    public void fetchUsers() {
        Call<List<User>> getUserListCall = userService.getAllUsers();
        getUserListCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                userList.clear();
                userList.addAll(response.body());
                try {
                    requireActivity().runOnUiThread(adapter::notifyDataSetChanged);
                } catch (Exception ignored) {}
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void updateUserCredits(int pos, double credits) {
        userList.get(pos).setCredits(credits);
        Call<User> updateUserCreditsCall = userService.updateUser(userList.get(pos));
        try {
            Context context = requireContext();
            updateUserCreditsCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    Toast.makeText(context, "User credits have been updated successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Failed to update user credits", Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception ignored) {}
    }


}