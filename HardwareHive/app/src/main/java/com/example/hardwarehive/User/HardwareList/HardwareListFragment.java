package com.example.hardwarehive.User.HardwareList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.API.HardwareService;
import com.example.hardwarehive.API.Retrofit2Client;
import com.example.hardwarehive.API.ShoppingCartService;
import com.example.hardwarehive.MainActivity;
import com.example.hardwarehive.R;
import com.example.hardwarehive.User.CategoryPicker.CategoryPickerFragment;
import com.example.hardwarehive.User.ShoppingCart.HardwareOrder;
import com.example.hardwarehive.User.ShoppingCart.ShoppingCart;
import com.example.hardwarehive.User.ShoppingCart.ShoppingCartFragment;
import com.example.hardwarehive.model.Hardware;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HardwareListFragment extends Fragment implements HardwareListInterface {
    private List<Hardware> hardwareList;
    private HardwareService hardwareService;
    private ShoppingCartService shoppingCartService;
    private String type;
    private View root;

    public HardwareListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_hardware_list, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.UserRecyclerView);

        TextView title = root.findViewById(R.id.userListTitleTextView);

        Bundle data = getArguments();
        if(data != null && data.containsKey("title")){
            type = data.getString("title");
            assert type != null;
            String titleName = capitalize(type.replace('_', ' '));
            title.setText(getString(R.string.inventory, titleName));

            hardwareService = Retrofit2Client.createService(HardwareService.class);
            shoppingCartService = Retrofit2Client.createService(ShoppingCartService.class);

            hardwareList = new ArrayList<>();
            HardwareListAdapter adapter = new HardwareListAdapter(getContext(), hardwareList, this);

            fetchItems(adapter, hardwareList, type);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            goBackToCategoriesButtonInitializer();
            initializeGoToShoppingCardScreenButton();
        } else if(data!=null && data.containsKey("itemName")) {
            title.setText(getString(R.string.inventory, "Similar Items"));
            hardwareService = Retrofit2Client.createService(HardwareService.class);
            shoppingCartService = Retrofit2Client.createService(ShoppingCartService.class);

            hardwareList = new ArrayList<>();
            HardwareListAdapter adapter = new HardwareListAdapter(getContext(), hardwareList, this);

            fetchSimilarItems(adapter, hardwareList, data.getString("itemName"));

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            goBackToCategoriesButtonInitializer();
            initializeGoToShoppingCardScreenButton();
        } else {
            title.setText(getString(R.string.inventory, "Item"));
            goBackToCategoriesButtonInitializer();
        }

        return root;
    }

    // Method used by the goBackButton to return to the Category Picker screen
    public void goBackToCategoriesButtonInitializer() {
        root.findViewById(R.id.goBackButton).setOnClickListener(v -> {
            CategoryPickerFragment categoryPickerFragment = new CategoryPickerFragment();
            ((MainActivity) requireActivity()).switchFragment(categoryPickerFragment);
        });
    }

    // Method used to capitalize the first letter of every word in a given string
    public String capitalize(String s){
        String[] words = s.split("\\s+");

        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return capitalized.toString().trim();
    }

    private void fetchSimilarItems(HardwareListAdapter adapter, List<Hardware> hardwareList, String itemName) {
        Call<List<Hardware>> call = hardwareService.getSimilarHardware(itemName);
        call.enqueue(new Callback<List<Hardware>>() {

            @Override
            public void onResponse(@NonNull Call<List<Hardware>> call, @NonNull Response<List<Hardware>> response) {
                hardwareList.clear();
                hardwareList.addAll(response.body());
                try{
                    requireActivity().runOnUiThread(adapter::notifyDataSetChanged);
                } catch(Exception ignored){}
            }

            @Override
            public void onFailure(@NonNull Call<List<Hardware>> call, @NonNull Throwable t) {
            }
        });
    }

    // Method used to fetch the items from the api and load them to the recycler view
    public void fetchItems(HardwareListAdapter adapter, List<Hardware> hardwareList, String type){
        Call<List<Hardware>> call = hardwareService.getHardwareByCategory(type);
        call.enqueue(new Callback<List<Hardware>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hardware>> call, @NonNull Response<List<Hardware>> response) {
                hardwareList.clear();
                hardwareList.addAll(response.body());
                try{
                    requireActivity().runOnUiThread(adapter::notifyDataSetChanged);
                } catch(Exception ignored){}
            }

            @Override
            public void onFailure(@NonNull Call<List<Hardware>> call, @NonNull Throwable t) {
          
            }
        });
    }

    @Override
    public boolean addItemToCart(int pos, int quantity) {
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        Call<ShoppingCart> getShoppingCartCall = shoppingCartService.getCart(userEmail);
        getShoppingCartCall.enqueue(new Callback<ShoppingCart>() {
            @Override
            public void onResponse(@NonNull Call<ShoppingCart> call, @NonNull Response<ShoppingCart> response) {
                ShoppingCart shoppingCart = response.body();

                if(hardwareList.get(pos).getQuantity()>=quantity && shoppingCart !=null) {
                    Call<HardwareOrder> addItemCall = shoppingCartService.addItemToCart(userEmail, new HardwareOrder(hardwareList.get(pos).getId(), shoppingCart.getId(), quantity, false));
                    addItemCall.enqueue(new Callback<HardwareOrder>() {
                        @Override
                        public void onResponse(@NonNull Call<HardwareOrder> call, @NonNull Response<HardwareOrder> response) {

                        }

                        @Override
                        public void onFailure(@NonNull Call<HardwareOrder> call, @NonNull Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShoppingCart> call, @NonNull Throwable t) {

            }
        });
        return hardwareList.get(pos).getQuantity() >= quantity;
    }

    @Override
    public void removeItemFromCart(int pos) {
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        Call<ShoppingCart> getShoppingCartCall = shoppingCartService.getCart(userEmail);
        getShoppingCartCall.enqueue(new Callback<ShoppingCart>() {
            @Override
            public void onResponse(@NonNull Call<ShoppingCart> call, @NonNull Response<ShoppingCart> response) {
                ShoppingCart shoppingCart = response.body();
                if(shoppingCart!=null) {
                    Call<Void> removeItemCall = shoppingCartService.removeItemFromCartUsingHardwareId(userEmail, hardwareList.get(pos).getId());
                    removeItemCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShoppingCart> call, @NonNull Throwable t) {

            }
        });

    }

    public void initializeGoToShoppingCardScreenButton() {
        root.findViewById(R.id.goToShoppingCartButton).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", type);

            ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
            shoppingCartFragment.setArguments(bundle);

            ((MainActivity) requireActivity()).switchFragment(shoppingCartFragment);
        });
    }
}