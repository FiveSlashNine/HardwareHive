package com.example.hardwarehive.Admin.HardwareList;

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

import com.example.hardwarehive.API.HardwareService;
import com.example.hardwarehive.API.Retrofit2Client;
import com.example.hardwarehive.Admin.CategoryPicker.AdminCategoryPickerFragment;
import com.example.hardwarehive.MainActivity;
import com.example.hardwarehive.R;
import com.example.hardwarehive.model.Hardware;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHardwareListFragment extends Fragment implements AdminHardwareListInterface {
    private AdminHardwareListAdapter adapter;
    private List<Hardware> hardwareList;
    private HardwareService hardwareService;
    private String type;
    private View root;

    public AdminHardwareListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_admin_hardware_list, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.UserRecyclerView);

        TextView title = root.findViewById(R.id.userListTitleTextView);

        Bundle data = getArguments();
        if(data != null && data.containsKey("title") && data.getString("title")!=null){
            type = data.getString("title");
            assert type != null;
            String titleName = capitalize(type.replace('_', ' '));
            title.setText(getString(R.string.inventory, titleName));

            hardwareService = Retrofit2Client.createService(HardwareService.class);

            hardwareList = new ArrayList<>();
            adapter = new AdminHardwareListAdapter(getContext(), hardwareList, this);

            fetchItems(adapter, hardwareList, type);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            addNewItemToTheListButtonInitializer();

            goBackToCategoriesButtonInitializer();
        } else if(data!=null && data.containsKey("itemName")) {
            title.setText(getString(R.string.inventory, "Similar Items"));
            hardwareService = Retrofit2Client.createService(HardwareService.class);

            hardwareList = new ArrayList<>();
            adapter = new AdminHardwareListAdapter(getContext(), hardwareList, this);

            fetchSimilarItems(adapter, hardwareList, data.getString("itemName"));

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            root.findViewById(R.id.addItemToList).setVisibility(View.GONE);
            goBackToCategoriesButtonInitializer();
        } else {
            title.setText(getString(R.string.inventory, "Item"));
            goBackToCategoriesButtonInitializer();
        }

        return root;
    }

    private void fetchSimilarItems(AdminHardwareListAdapter adapter, List<Hardware> hardwareList, String itemName) {
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

    // Method used by the goBackButton to return to the Category Picker screen
    public void goBackToCategoriesButtonInitializer() {
        root.findViewById(R.id.goBackButton).setOnClickListener(v -> {
            AdminCategoryPickerFragment categoryPickerFragment = new AdminCategoryPickerFragment();
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

    public void addNewItemToTheListButtonInitializer() {
        root.findViewById(R.id.addItemToList).setOnClickListener(v -> {
            if(!hardwareList.get(hardwareList.size()-1).getName().equals(" ")){
                Call<Hardware> call = hardwareService.createHardware(new Hardware(type));
                call.enqueue(new Callback<Hardware>() {
                    @Override
                    public void onResponse(@NonNull Call<Hardware> call, @NonNull Response<Hardware> response) {
                        if(response.body()!=null){
                            hardwareList.add(response.body());
                            try{
                                requireActivity().runOnUiThread(()->adapter.notifyItemInserted(hardwareList.size()-1));
                            } catch(Exception ignored){}
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Hardware> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }

    // Method used to fetch the items from the api and load them to the recycler view
    public void fetchItems(AdminHardwareListAdapter adapter, List<Hardware> hardwareList, String type){
        Call<List<Hardware>> call = hardwareService.getHardwareByCategory(type);
        call.enqueue(new Callback<List<Hardware>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hardware>> call, @NonNull Response<List<Hardware>> response) {
                hardwareList.clear();
                System.out.println(response.body());
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

    // Method used to delete an item
    @Override
    public void removeCard(int position) {
        if(type==null) type = pascalToSnake(hardwareList.get(position).getClass().getSimpleName());
        Call<Void> call = hardwareService.delete(hardwareList.get(position).getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                hardwareList.remove(position);
                requireActivity().runOnUiThread(()->adapter.notifyItemRemoved(position));
                Toast.makeText(getContext(), "Item has been deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                hardwareList.remove(position);
                requireActivity().runOnUiThread(()->adapter.notifyItemRemoved(position));
                Toast.makeText(getContext(), "Item failed to get deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method used to update an item
    @Override
    public void updateCard(int position) {
        if(type==null) type = pascalToSnake(hardwareList.get(position).getClass().getSimpleName());
        Call<Hardware> call = hardwareService.update(hardwareList.get(position));
        call.enqueue(new Callback<Hardware>() {
            @Override
            public void onResponse(@NonNull Call<Hardware> call, @NonNull Response<Hardware> response) {
                Toast.makeText(getContext(), "Item has been updated", Toast.LENGTH_SHORT).show();
                requireActivity().runOnUiThread(()->adapter.notifyItemChanged(position));
            }

            @Override
            public void onFailure(@NonNull Call<Hardware> call, @NonNull Throwable t) {
            }
        });
    }

    public static String pascalToSnake(String pascal) {
        return pascal.replaceAll("(?<!^)(?=[A-Z])", "_").toLowerCase();
    }
}