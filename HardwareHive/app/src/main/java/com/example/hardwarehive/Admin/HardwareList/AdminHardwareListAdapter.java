package com.example.hardwarehive.Admin.HardwareList;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.R;
import com.example.hardwarehive.model.Hardware;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AdminHardwareListAdapter extends RecyclerView.Adapter<AdminHardwareListAdapter.MyViewHolder> {
    private final Context context;
    private final List<Hardware> hardwareList;
    private final AdminHardwareListInterface hardwareListInterface;

    public AdminHardwareListAdapter(Context context, List<Hardware> hardwareList, AdminHardwareListInterface hardwareListInterface){
        this.context = context;
        this.hardwareList = hardwareList;
        this.hardwareListInterface = hardwareListInterface;
    }

    @NonNull
    @Override
    public AdminHardwareListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hardware_admin_item, parent, false);
        return new AdminHardwareListAdapter.MyViewHolder(view, context, hardwareList, hardwareListInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHardwareListAdapter.MyViewHolder holder, int position) {
        // Loads the default and saved images for the image picker
        String imagePath = hardwareList.get(position).getImageURL();
        if(!imagePath.equals(" "))
            Picasso.get().load(imagePath).placeholder(R.drawable.ic_launcher_foreground).fit().error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.name.setText(hardwareList.get(position).getName());
        holder.quantity.setText(String.valueOf(hardwareList.get(position).getQuantity()));
        holder.price.setText(String.valueOf(hardwareList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return hardwareList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private EditText name, quantity, price, imageUrl;

        public MyViewHolder(@NonNull View itemView, Context context, List<Hardware> hardwareList, AdminHardwareListInterface hardwareListInterface) {
            super(itemView);

            setupNameEditText(hardwareList, hardwareListInterface);

            setupQuantityEditText(hardwareList, hardwareListInterface);

            setupPriceEditText(hardwareList, hardwareListInterface);

            setupImageView(hardwareList);
            setupImageUrlEditText(hardwareList, hardwareListInterface);

            setupExpandingCards(context, hardwareList);

            setupDeleteButton(hardwareListInterface);
            setupUpdateButton(hardwareListInterface);
        }

        // Method used to set listeners in order to update the item's name without having to expand the card
        private void setupNameEditText(List<Hardware> hardwareList, AdminHardwareListInterface hardwareListInterface) {
            name = itemView.findViewById(R.id.nameEditText);

            name.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        String val = String.valueOf(name.getText());
                        if(!val.equals(hardwareList.get(pos).getName())) {
                            hardwareList.get(pos).setName(val);
                            hardwareListInterface.updateCard(pos);
                        }
                    }
                    return true;
                }

                return false;
            });

            name.setOnFocusChangeListener((v, hasFocus) -> {
                if(!hasFocus){
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        String val = String.valueOf(name.getText());
                        if(!val.equals(hardwareList.get(pos).getName())) {
                            hardwareList.get(pos).setName(val);
                            hardwareListInterface.updateCard(pos);
                        }
                    }
                }
            });
        }

        // Method used to set listeners in order to update the item's quantity without having to expand the card
        private void setupQuantityEditText(List<Hardware> hardwareList, AdminHardwareListInterface hardwareListInterface) {
            quantity = itemView.findViewById(R.id.quantityEditText);

            quantity.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        try{
                            int val = Integer.parseInt(String.valueOf(quantity.getText()));
                            if(val != hardwareList.get(pos).getQuantity()) {
                                hardwareList.get(pos).setQuantity(val);
                                hardwareListInterface.updateCard(pos);
                            }
                        } catch (Exception ignored) {
                            quantity.setText(String.valueOf(hardwareList.get(pos).getQuantity()));
                        }

                    }
                    return true;
                }

                return false;
            });

            quantity.setOnFocusChangeListener((v, hasFocus) -> {
                if(!hasFocus){
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        try{
                            int val = Integer.parseInt(String.valueOf(quantity.getText()));
                            if(val != hardwareList.get(pos).getQuantity()) {
                                hardwareList.get(pos).setQuantity(val);
                                hardwareListInterface.updateCard(pos);
                            }
                        } catch (Exception ignored) {
                            quantity.setText(String.valueOf(hardwareList.get(pos).getQuantity()));
                        }
                    }
                }
            });
        }

        // Method used to set listeners in order to update the item's price without having to expand the card
        private void setupPriceEditText(List<Hardware> hardwareList, AdminHardwareListInterface hardwareListInterface) {
            price = itemView.findViewById(R.id.priceEditText);
            price.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        try{
                            float p = Float.parseFloat(String.valueOf(price.getText()));
                            if(hardwareList.get(pos).getPrice()!=p) {
                                hardwareList.get(pos).setPrice(p);
                                hardwareListInterface.updateCard(pos);
                            }
                        } catch(Exception e) {
                            price.setText(String.valueOf(hardwareList.get(pos).getPrice()));
                        }
                    }
                    return true;
                }
                return false;
            });

            price.setOnFocusChangeListener((v, hasFocus) -> {
                if(!hasFocus) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        try{
                            float p = Float.parseFloat(String.valueOf(price.getText()));
                            if(hardwareList.get(pos).getPrice()!=p) {
                                hardwareList.get(pos).setPrice(p);
                                hardwareListInterface.updateCard(pos);
                            }
                        } catch(Exception e) {
                            price.setText(String.valueOf(hardwareList.get(pos).getPrice()));
                        }
                    }
                }
            });
        }

        // Method used to reveal the image url edit text in order for the user to be able to update the image url
        private void setupImageView(List<Hardware> hardwareList) {
            imageView = itemView.findViewById(R.id.imageView);

            imageView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    if(imageUrl.getVisibility() != View.VISIBLE) {
                        imageUrl.setVisibility(View.VISIBLE);
                        imageUrl.setText(hardwareList.get(pos).getImageURL());
                    } else {
                        imageUrl.setVisibility(View.GONE);
                    }
                }
            });
        }

        // Method used to set listeners in order to update the item's image without having to expand the card
        private void setupImageUrlEditText(List<Hardware> hardwareList, AdminHardwareListInterface hardwareListInterface) {
            imageUrl = itemView.findViewById(R.id.imageEditText);

            imageUrl.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        try{
                            String url = String.valueOf(imageUrl.getText());
                            if(!hardwareList.get(pos).getImageURL().equals(url)) {
                                hardwareList.get(pos).setImageURL(url);
                                hardwareListInterface.updateCard(pos);
                            }
                        } catch(Exception e) {
                            imageUrl.setText(String.valueOf(hardwareList.get(pos).getImageURL()));
                        }
                    }
                    return true;
                }
                return false;
            });

            imageUrl.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        try{
                            String url = String.valueOf(imageUrl.getText());
                            if(!hardwareList.get(pos).getImageURL().equals(url)) {
                                hardwareList.get(pos).setImageURL(url);
                                hardwareListInterface.updateCard(pos);
                            }
                        } catch(Exception e) {
                            imageUrl.setText(String.valueOf(hardwareList.get(pos).getImageURL()));
                        }
                    }
                }
            });
        }

        // Method to make the cards expandable
        private void setupExpandingCards(Context context, List<Hardware> hardwareList) {
            itemView.findViewById(R.id.itemCard).setOnClickListener(v -> {
                RecyclerView recyclerView = itemView.findViewById(R.id.moreInfoListView);

                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    recyclerView.setAdapter(new AdminExtendedHardwareListAdapter(context, hardwareList.get(pos)));

                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                    ConstraintLayout layout = itemView.findViewById(R.id.moreInfoLayout);
                    layout.setVisibility((layout.getVisibility() != View.VISIBLE) ? View.VISIBLE : View.GONE);
                }
            });
        }

        private void setupDeleteButton(AdminHardwareListInterface hardwareListInterface) {
            itemView.findViewById(R.id.deleteButton).setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    hardwareListInterface.removeCard(pos);
                }
            });
        }

        public void setupUpdateButton(AdminHardwareListInterface hardwareListInterface) {
            itemView.findViewById(R.id.updateButton).setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    hardwareListInterface.updateCard(pos);
                }
            });
        }
    }
}