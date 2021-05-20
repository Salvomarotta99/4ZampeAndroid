package com.unimib.App4ZampeAndroid.Views;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.R;


public class BreedDetailFragment extends Fragment {

    private Breed b;
    private Activity activity;


    public BreedDetailFragment(Breed b, Activity activity) {
        this.b = b;
        this.activity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breeddetail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imagePetDetail = view.findViewById(R.id.imagePetDetail);
        TextView namePetDetail = view.findViewById(R.id.namePetDetail);
        TextView temperamentPetDetail = view.findViewById(R.id.temperamentPetDetail);
        TextView lifespanPetDetail = view.findViewById(R.id.lifespanPetDetail);
        TextView weightPetDetail = view.findViewById(R.id.weightPetDetail);
        TextView heightPetDetail = view.findViewById(R.id.heightPetDetail);
        TextView wikiPetDetail = view.findViewById(R.id.wikiPetDetail);

        Picasso.get().load(b.getImage().getUrl()).into(imagePetDetail);
        namePetDetail.setText(b.getName());
        temperamentPetDetail.setText(b.getTemperament());
        lifespanPetDetail.setText(b.getLife_span());
        weightPetDetail.setText(b.getWeight().toString());
        heightPetDetail.setText(b.getHeight().toString());
        wikiPetDetail.setText(b.getWikipedia_url());

    }
}