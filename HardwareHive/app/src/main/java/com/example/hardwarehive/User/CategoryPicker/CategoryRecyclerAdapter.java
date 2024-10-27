package com.example.hardwarehive.User.CategoryPicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.Admin.CategoryPicker.CategoryPickerInterface;
import com.example.hardwarehive.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private final List<String> imageList;
    private final CategoryPickerInterface categoryPickerInterface;

    public CategoryRecyclerAdapter(Context context, List<String> imageList, CategoryPickerInterface categoryPickerInterface){
        this.context = context;
        this.imageList = imageList;
        this.categoryPickerInterface = categoryPickerInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_category_image, parent, false);
        return new MyViewHolder(view, categoryPickerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(imageList.get(position)).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.imageView.setTag(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;

        public MyViewHolder(@NonNull View itemView, CategoryPickerInterface imagePickerInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.categoryImage);
            imageView.setOnClickListener(view -> {
                if(imagePickerInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        imagePickerInterface.moveToCategory((String) imageView.getTag());
                    }
                }
            });
        }
    }
}