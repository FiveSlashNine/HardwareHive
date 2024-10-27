package com.example.hardwarehive.User.HardwareList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.R;
import com.example.hardwarehive.model.HardwareSpec;

import java.util.List;


public class ExtendedHardwareListAdapter extends RecyclerView.Adapter<ExtendedHardwareListAdapter.MyViewHolder> {
    private final Context context;
    private final List<HardwareSpec> hardwareSpecList;

    public ExtendedHardwareListAdapter(Context context, List<HardwareSpec> hardwareSpecList){
        this.context = context;
        this.hardwareSpecList = hardwareSpecList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.extended_hardware_data_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.hardwareInfo.setText(context.getString(R.string.word_before_colon, hardwareSpecList.get(position).getName()));
        holder.hardwareVal.setText(hardwareSpecList.get(position).getVal());
    }

    @Override
    public int getItemCount() {
        return hardwareSpecList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView hardwareInfo, hardwareVal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hardwareInfo = itemView.findViewById(R.id.hardwareInfo);
            hardwareVal = itemView.findViewById(R.id.hardwareVal);
        }
    }
}
