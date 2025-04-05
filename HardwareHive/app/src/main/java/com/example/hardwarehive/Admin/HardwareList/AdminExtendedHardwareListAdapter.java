package com.example.hardwarehive.Admin.HardwareList;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.R;
import com.example.hardwarehive.model.Hardware;
import com.example.hardwarehive.model.HardwareSpec;

import java.util.List;

public class AdminExtendedHardwareListAdapter extends RecyclerView.Adapter<AdminExtendedHardwareListAdapter.MyViewHolder> {
    private final Context context;
    private final List<HardwareSpec> hardwareSpecList;

    public AdminExtendedHardwareListAdapter(Context context, Hardware hardware){
        this.context = context;
        this.hardwareSpecList = hardware.getHardwareSpecs();
    }

    @NonNull
    @Override
    public AdminExtendedHardwareListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.extended_hardware_data_admin_item, parent, false);
        return new AdminExtendedHardwareListAdapter.MyViewHolder(view, hardwareSpecList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.hardwareInfo.setText(context.getString(R.string.word_before_colon, hardwareSpecList.get(position).getName()));
        holder.hardwareVal.setText(hardwareSpecList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return hardwareSpecList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView hardwareInfo, hardwareVal;

        public MyViewHolder(@NonNull View itemView, List<HardwareSpec> hardwareSpecList) {
            super(itemView);
            hardwareInfo = itemView.findViewById(R.id.hardwareInfo);
            hardwareVal = itemView.findViewById(R.id.hardwareVal);

            hardwareVal.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    hardwareSpecList.get(getAdapterPosition()).setValue(String.valueOf(s));
                }
            });
        }
    }
}
