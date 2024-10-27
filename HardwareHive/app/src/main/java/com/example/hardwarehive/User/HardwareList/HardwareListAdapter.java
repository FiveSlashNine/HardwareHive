package com.example.hardwarehive.User.HardwareList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.R;
import com.example.hardwarehive.model.Hardware;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HardwareListAdapter extends RecyclerView.Adapter<HardwareListAdapter.MyViewHolder> {
    private final Context context;
    private final List<Hardware> hardwareList;
    private final HardwareListInterface hardwareListInterface;

    public HardwareListAdapter(Context context, List<Hardware> hardwareList, HardwareListInterface hardwareListInterface) {
        this.context = context;
        this.hardwareList = hardwareList;
        this.hardwareListInterface = hardwareListInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hardware_item, parent, false);
        return new MyViewHolder(view, context, hardwareList, hardwareListInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
        private final ImageView imageView;
        private final TextView name, quantity, price;
        private final ImageButton addItemToCart, removeItemFromCart;

        public MyViewHolder(@NonNull View itemView, Context context, List<Hardware> hardwareList, HardwareListInterface hardwareListInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.nameTextView);
            quantity = itemView.findViewById(R.id.quantityTextView);
            price = itemView.findViewById(R.id.priceTextView);
            addItemToCart = itemView.findViewById(R.id.addItemToCartButton);
            removeItemFromCart = itemView.findViewById(R.id.removeItemFromCartButton);

            setupExpandingCards(context, hardwareList);

            initializeAddItemToShoppingCartButton(hardwareListInterface);
            initializeRemoveItemToShoppingCartButton(hardwareListInterface);
        }

        private void initializeAddItemToShoppingCartButton(HardwareListInterface hardwareListInterface) {
            addItemToCart.setOnClickListener(v -> {
               int pos = getAdapterPosition();
               if(pos!=RecyclerView.NO_POSITION){
                   if(hardwareListInterface.addItemToCart(pos, 1)) {
                       removeItemFromCart.setVisibility(View.VISIBLE);
                       addItemToCart.setVisibility(View.INVISIBLE);
                   }
               }
           });
        }

        private void initializeRemoveItemToShoppingCartButton(HardwareListInterface hardwareListInterface) {
            itemView.findViewById(R.id.removeItemFromCartButton).setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    hardwareListInterface.removeItemFromCart(pos);
                    addItemToCart.setVisibility(View.VISIBLE);
                    removeItemFromCart.setVisibility(View.INVISIBLE);
                }
            });
        }


        // Method to make the cards expandable
        private void setupExpandingCards(Context context, List<Hardware> hardwareList) {
            itemView.findViewById(R.id.itemCard).setOnClickListener(v -> {
                RecyclerView recyclerView = itemView.findViewById(R.id.moreInfoListView);

                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    recyclerView.setAdapter(new ExtendedHardwareListAdapter(context, hardwareList.get(pos).getSpecs()));

                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                    ConstraintLayout layout = itemView.findViewById(R.id.moreInfoLayout);
                    layout.setVisibility((layout.getVisibility() != View.VISIBLE) ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
}