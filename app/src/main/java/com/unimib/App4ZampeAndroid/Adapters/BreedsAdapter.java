package com.unimib.App4ZampeAndroid.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.R;

import java.util.List;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder> {

    private  List<Breed> breedList;
    private OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void onClick(Breed b);
    }

    public BreedsAdapter(List<Breed> breedList, OnItemClickListener listener) {
        this.breedList = breedList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public BreedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.breed_item, parent, false);

        return new BreedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedsViewHolder holder, int position) {
        holder.bind(breedList.get(position));
    }

    @Override
    public int getItemCount() {
        if(breedList != null)
        return breedList.size();
        return 0;
    }

    public class BreedsViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTextView;
        TextView temperamentTextView;

        public BreedsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.breed_name);
            temperamentTextView = itemView.findViewById(R.id.breed_temperament);
        }

        public void bind(Breed b)
        {
            nameTextView.setText(b.getName());
            temperamentTextView.setText(b.getTemperament());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(b);
                }
            });
        }
    }
}
