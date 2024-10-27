package com.example.hardwarehive.Admin.UserList;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.R;
import com.example.hardwarehive.model.User;

import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private final Context context;
    private final List<User> userList;
    private final UserListInterface userListInterface;

    public UserListAdapter(Context context, List<User> userList, UserListInterface userListInterface){
        this.context = context;
        this.userList = userList;
        this.userListInterface = userListInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_card, parent, false);
        return new MyViewHolder(view, userListInterface, userList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.emailTextView.setText(userList.get(position).getEmail());
        holder.emailTextView.setMovementMethod(new ScrollingMovementMethod());

        holder.usernameTextView.setText(userList.get(position).getUsername());
        holder.usernameTextView.setMovementMethod(new ScrollingMovementMethod());

        holder.creditsEditText.setText(String.valueOf(userList.get(position).getCredits()));
        holder.creditsEditText.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView usernameTextView, emailTextView;
        private final EditText creditsEditText;

        public MyViewHolder(@NonNull View itemView, UserListInterface userListInterface, List<User> userList) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            creditsEditText = itemView.findViewById(R.id.creditsTextNumber);

            creditsEditText.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(userListInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            try {
                                double credits = Double.parseDouble(String.valueOf(creditsEditText.getText()));
                                userListInterface.updateUserCredits(pos, credits);
                            } catch (Exception ignored) {
                                creditsEditText.setText(String.valueOf(userList.get(pos).getCredits()));
                            }
                        }
                    }
                    return true;
                }
                return false;
            });

            creditsEditText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    if(userListInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            try {
                                double credits = Double.parseDouble(String.valueOf(creditsEditText.getText()));
                                userListInterface.updateUserCredits(pos, credits);
                            } catch (Exception ignored) {
                                creditsEditText.setText(String.valueOf(userList.get(pos).getCredits()));
                            }
                        }
                    }
                }
            });
        }
    }
}
