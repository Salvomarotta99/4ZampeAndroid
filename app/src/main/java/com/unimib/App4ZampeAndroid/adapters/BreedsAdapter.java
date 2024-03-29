package com.unimib.App4ZampeAndroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.R;
import com.unimib.App4ZampeAndroid.repositories.BreedsRepository;

import java.util.ArrayList;
import java.util.List;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder> implements Filterable {

    private List<Breed> breedList;
    private List<Breed> breedListAll;
    private OnItemClickListener listener;
    private BreedsRepository breedsRepository;



    public interface OnItemClickListener
    {
        void onClick(Breed b);
    }

    public BreedsAdapter(List<Breed> breedList, OnItemClickListener listener) {
        this.breedList = breedList;
        this.listener = listener;
        this.breedListAll = breedList;
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
        ImageView imageViewBreed;

        public BreedsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.breed_name);
            imageViewBreed = itemView.findViewById(R.id.breed_image);
        }

        public void bind(Breed b)
        {
            nameTextView.setText(b.getName());
            if(b.getImage() != null){
                Picasso.get().load(b.getImage().getUrl()).into(imageViewBreed);
            }
            else {
                Picasso.get().load(R.drawable.placeholder_immagine).into(imageViewBreed);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(b);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            List<Breed> filteredList = new ArrayList<>();
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredList = breedListAll;
                } else {

                    for (Breed b : breedListAll) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (b.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(b);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                breedList = (ArrayList<Breed>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
