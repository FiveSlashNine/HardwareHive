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
import com.example.hardwarehive.model.Hardware;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

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
    private List<Hardware> hardwareList;
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
        hardwareList = new ArrayList<>();
        adapter = new ShoppingCartAdapter(getContext(), hardwareList, orderList,this);

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
                String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
                Call<Void> shoppingCartCall = shoppingCartService.purchase(userEmail);
                shoppingCartCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), "Purchase Successful", Toast.LENGTH_SHORT).show();
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
                            String message = "Unknown error";

                            try {
                                String errorBody = response.errorBody() != null ? response.errorBody().string() : null;

                                if (errorBody != null) {
                                    JSONObject jsonObject = new JSONObject(errorBody);

                                    if (jsonObject.has("message")) {
                                        message = jsonObject.getString("message");
                                    }
                                }
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void removeItemFromCart(int pos) {
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();


        Call<Void> removeItemCall = shoppingCartService.removeItemFromCart(userEmail, orderList.get(pos).getId());
        removeItemCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                orderList.remove(pos);
                try {
                    requireActivity().runOnUiThread(()->adapter.notifyItemRemoved(pos));
                } catch(Exception ignored) {}
                getNewTotalAmount(userEmail);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                getNewTotalAmount(userEmail);
            }
        });
    }

    private void getNewTotalAmount(String userEmail) {
        Call<Double> getTotalAMount = shoppingCartService.getTotalCost(userEmail);
        getTotalAMount.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(@NonNull Call<Double> call, @NonNull Response<Double> response) {
                if (response.body()!=null) {
                    totalAmount = response.body();
                    requireActivity().runOnUiThread(() -> ((TextView) root.findViewById(R.id.amountTextView)).setText(String.valueOf(totalAmount)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Double> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void updateItemQuantity(int pos, int quantity) {
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

        orderList.get(pos).setQuantity(quantity);
        Call<HardwareOrder> shoppingCartCall = shoppingCartService.updateItemInCart(userEmail, orderList.get(pos));
        shoppingCartCall.enqueue(new Callback<HardwareOrder>() {
            @Override
            public void onResponse(@NonNull Call<HardwareOrder> call, @NonNull Response<HardwareOrder> response) {
                getNewTotalAmount(userEmail);
            }

            @Override
            public void onFailure(@NonNull Call<HardwareOrder> call, @NonNull Throwable t) {
                getNewTotalAmount(userEmail);
            }
        });

    }

    public void loadOrderList(List<HardwareOrder> orderList){
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        Call<List<HardwareOrder>> shoppingCartCall = shoppingCartService.getCartItems(userEmail);

        shoppingCartCall.enqueue(new Callback<List<HardwareOrder>>() {
            @Override
            public void onResponse(@NonNull Call<List<HardwareOrder>> call, @NonNull Response<List<HardwareOrder>> response) {
                new Thread( () -> {
                    if (response.body()!=null) {
                        List<HardwareOrder> shoppingCart = response.body();
                        orderList.clear();
                        orderList.addAll(shoppingCart);
                        loadHardwareList();
                        getNewTotalAmount(userEmail);
                    }
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<HardwareOrder>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loadHardwareList() {
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        ShoppingCartService shoppingCartService = Retrofit2Client.createService(ShoppingCartService.class);
        Call<List<Hardware>> getHardwareListCall = shoppingCartService.getHardwareOrderItem(userEmail);
        getHardwareListCall.enqueue(new Callback<List<Hardware>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hardware>> call, @NonNull Response<List<Hardware>> response) {
                hardwareList.clear();
                hardwareList.addAll(response.body());
                try {
                    requireActivity().runOnUiThread(adapter::notifyDataSetChanged);
                } catch(Exception ignored) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Hardware>> call, @NonNull Throwable t) {
            }
        });
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