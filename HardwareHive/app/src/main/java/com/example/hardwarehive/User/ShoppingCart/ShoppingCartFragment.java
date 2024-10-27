package com.example.hardwarehive.User.ShoppingCart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardwarehive.API.Retrofit2Client;
import com.example.hardwarehive.API.ShoppingCartService;
import com.example.hardwarehive.MainActivity;
import com.example.hardwarehive.R;
import com.example.hardwarehive.User.CategoryPicker.CategoryPickerFragment;
import com.example.hardwarehive.User.HardwareList.HardwareListFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartFragment extends Fragment implements HardwareOrderListInterface {
    private View root;
    private List<HardwareOrder> orderList;
    private ShoppingCartAdapter adapter;
    private ShoppingCartService shoppingCartService;
    private double totalAmount = 0;

    public ShoppingCartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        shoppingCartService = Retrofit2Client.createService(ShoppingCartService.class);

        RecyclerView recyclerView = root.findViewById(R.id.cartRecyclerView);

        orderList = new ArrayList<>();
        adapter = new ShoppingCartAdapter(getContext(), orderList, this);

        loadOrderList(orderList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        purchaseButtonInitializer();

        goBackToHardwareListButtonInitializer();

        return root;
    }

    private void purchaseButtonInitializer() {
        root.findViewById(R.id.checkoutButton).setOnClickListener(v -> {
            boolean hasQuantity = false;
            for(HardwareOrder ho : orderList) {
                if(ho.getQuantity()!=0) {
                    hasQuantity = true;
                    break;
                }
            }

            if(hasQuantity) {
                String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                Call<String> shoppingCartCall = shoppingCartService.purchaseCart(userId);
                shoppingCartCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                            List<HardwareOrder> itemsToRemove = new ArrayList<>();
                            for (int i = 0; i < orderList.size(); i++) {
                                if (orderList.get(i).getQuantity() != 0) {
                                    itemsToRemove.add(orderList.get(i));
                                }
                            }

                            orderList.removeAll(itemsToRemove);
                            requireActivity().runOnUiThread(adapter::notifyDataSetChanged);
                            totalAmount = 0;
                            requireActivity().runOnUiThread(() -> ((TextView) root.findViewById(R.id.amountTextView)).setText(String.valueOf(totalAmount)));
                        } else {
                            try {
                                String message = response.errorBody().string();
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            } catch (Exception ignored) {}
                            for(HardwareOrder order : orderList) {
                                if(order.getQuantity()>order.getItem().getQuantity()){
                                    Toast.makeText(getContext(), "we only have " + order.getItem().getQuantity() + " " + order.getItem().getName() + " in stock", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void removeItemFromCart(int pos) {
        totalAmount -= orderList.get(pos).getQuantity() * orderList.get(pos).getItem().getPrice();
        Call<Void> addItemCall = shoppingCartService.removeItemFromCart(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), orderList.get(pos));
        addItemCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                orderList.remove(pos);
                try {
                    requireActivity().runOnUiThread(()->adapter.notifyItemRemoved(pos));
                } catch(Exception ignored) {}
                requireActivity().runOnUiThread(()->((TextView)root.findViewById(R.id.amountTextView)).setText(String.valueOf(totalAmount)));
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                totalAmount += orderList.get(pos).getQuantity() * orderList.get(pos).getItem().getPrice();
                requireActivity().runOnUiThread(()->((TextView)root.findViewById(R.id.amountTextView)).setText(String.valueOf(totalAmount)));
            }
        });
    }

    @Override
    public void updateItemQuantity(int pos, int quantity) {
        totalAmount -= orderList.get(pos).getQuantity() * orderList.get(pos).getItem().getPrice();
        int oldQuantity = orderList.get(pos).getQuantity();
        orderList.get(pos).setQuantity(quantity);
        Call<Void> shoppingCartCall = shoppingCartService.updateItemFromCart(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), orderList.get(pos));
        shoppingCartCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                totalAmount += orderList.get(pos).getQuantity() * orderList.get(pos).getItem().getPrice();
                requireActivity().runOnUiThread(()->((TextView)root.findViewById(R.id.amountTextView)).setText(String.valueOf(totalAmount)));
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                totalAmount += oldQuantity * orderList.get(pos).getItem().getPrice();
                requireActivity().runOnUiThread(()->((TextView)root.findViewById(R.id.amountTextView)).setText(String.valueOf(totalAmount)));
            }
        });
    }

    public void loadOrderList(List<HardwareOrder> orderList){
        Call<ShoppingCart> shoppingCartCall = shoppingCartService.getCart(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        shoppingCartCall.enqueue(new Callback<ShoppingCart>() {
            @Override
            public void onResponse(@NonNull Call<ShoppingCart> call, @NonNull Response<ShoppingCart> response) {
                new Thread( () -> {
                    if (response.body()!=null) {
                        ShoppingCart shoppingCart = response.body();
                        orderList.clear();
                        orderList.addAll(shoppingCart.getOrderItems());
                        calcTotal();
                        try {
                            requireActivity().runOnUiThread(adapter::notifyDataSetChanged);
                            requireActivity().runOnUiThread(()->((TextView)root.findViewById(R.id.amountTextView)).setText(String.valueOf(totalAmount)));
                        } catch(Exception ignored) {}
                    }
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<ShoppingCart> call, @NonNull Throwable t) {

            }
        });
    }

    public void calcTotal() {
        totalAmount = 0;
        for(int i=0; i<orderList.size(); i++) {
            totalAmount += orderList.get(i).getQuantity() * orderList.get(i).getItem().getPrice();
        }
    }

    // Method used by the goBackButton to return to the hardware list
    public void goBackToHardwareListButtonInitializer() {
        root.findViewById(R.id.goBackButton).setOnClickListener(v -> {
            Bundle savedData = getArguments();
            if(savedData!=null && savedData.containsKey("title") && savedData.getString("title")!=null){
                HardwareListFragment hardwareListFragment = new HardwareListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", savedData.getString("title"));
                hardwareListFragment.setArguments(bundle);
                ((MainActivity) requireActivity()).switchFragment(hardwareListFragment);
            } else {
                CategoryPickerFragment categoryPickerFragment = new CategoryPickerFragment();
                ((MainActivity) requireActivity()).switchFragment(categoryPickerFragment);
            }
        });
    }
}