package com.example.hardwarehive.User.ShoppingCart;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.R;
import com.example.hardwarehive.model.Hardware;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private final Context context;
    private final List<Hardware> hardwareList;
    private final HardwareOrderListInterface orderListInterface;
    private final List<HardwareOrder> orderList;

    public ShoppingCartAdapter(Context context, List<Hardware> hardwareList, List<HardwareOrder> orderList, HardwareOrderListInterface orderListInterface) {
        this.context = context;
        this.orderListInterface = orderListInterface;
        this.hardwareList = hardwareList;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_item, parent, false);
        return new MyViewHolder(view, hardwareList, orderListInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String imagePath = hardwareList.get(position).getImageURL();
        if(!imagePath.equals(" "))
            Picasso.get().load(imagePath).placeholder(R.drawable.ic_launcher_foreground).fit().error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.name.setText(hardwareList.get(position).getName());
        holder.price.setText(String.valueOf(hardwareList.get(position).getPrice()));
        holder.quantity.setText(String.valueOf(orderList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private static final long UPDATE_DELAY_MS = 500;
        private final ImageView imageView;
        private final TextView name, price;
        private final EditText quantity;
        private final HardwareOrderListInterface orderListInterface;
        List<Hardware> orderList;

        private final Handler handler = new Handler(Looper.getMainLooper());

        public MyViewHolder(@NonNull View itemView, List<Hardware> orderList, HardwareOrderListInterface orderListInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.nameTextView);
            price = itemView.findViewById(R.id.priceTextView);
            quantity = itemView.findViewById(R.id.itemQuantityEditText);

            this.orderList = orderList;

            this.orderListInterface = orderListInterface;
            initializeItemQuantityEditText();
            initializeIncreaseItemQuantityButton();
            initializeDecreaseItemQuantityButton();

            removeItemFromCartListenerInitializer();
        }

        private void initializeDecreaseItemQuantityButton() {
            EditText editText = itemView.findViewById(R.id.itemQuantityEditText);
            itemView.findViewById(R.id.decreaseQuantityButton).setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    try {
                        int quantity = Integer.parseInt(String.valueOf(editText.getText()));
                        if(quantity>0) {
                            editText.setText(String.valueOf(quantity - 1));
                            scheduleUpdate(quantity-1);
                        } else {
                            orderListInterface.removeItemFromCart(pos);
                        }
                    } catch(Exception e) {
                        editText.setText(String.valueOf(orderList.get(pos)));
                    }
                }
            });
        }

        private void initializeIncreaseItemQuantityButton() {
            EditText editText = itemView.findViewById(R.id.itemQuantityEditText);
            itemView.findViewById(R.id.increaseQuantityButton).setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos!=RecyclerView.NO_POSITION){
                    try {
                        int quantity = Integer.parseInt(String.valueOf(editText.getText()));
                        if(quantity<=1000) {
                            editText.setText(String.valueOf(quantity + 1));
                            scheduleUpdate(quantity+1);
                        }
                    } catch(Exception e) {
                        editText.setText(String.valueOf(orderList.get(pos)));
                    }
                }
            });
        }

        private void removeItemFromCartListenerInitializer() {
            itemView.findViewById(R.id.itemCard).setOnLongClickListener(v -> {
                if(orderListInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        orderListInterface.removeItemFromCart(pos);
                        return true;
                    }
                }

                return false;
            });
        }

        // Adds event listeners to the itemQuantityEditTexts in order to update the shopping cart;
        private void initializeItemQuantityEditText() {
            EditText editText = itemView.findViewById(R.id.itemQuantityEditText);

            editText.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        try{
                            int quantity = Integer.parseInt(String.valueOf(editText.getText()));
                            if(quantity<=1000) orderListInterface.updateItemQuantity(pos, quantity);
                        } catch(Exception e) {
                            editText.setText(String.valueOf(orderList.get(pos)));
                        }
                    }
                    return true;
                }
                return false;
            });

            editText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        try{
                            int quantity = Integer.parseInt(String.valueOf(editText.getText()));
                            if(quantity<=1000) orderListInterface.updateItemQuantity(pos, quantity);
                        } catch(Exception e) {
                            editText.setText(String.valueOf(orderList.get(pos)));
                        }
                    }
                }
            });
        }

        private void scheduleUpdate(int quantity) {
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(() -> triggerUpdateQuantity(quantity), UPDATE_DELAY_MS);
        }

        private void triggerUpdateQuantity(int quantity) {
            int pos = getAdapterPosition();
            if(pos!=RecyclerView.NO_POSITION){
                try{
                    orderListInterface.updateItemQuantity(pos, quantity);
                } catch(Exception ignored) {}
            }
        }
    }
}