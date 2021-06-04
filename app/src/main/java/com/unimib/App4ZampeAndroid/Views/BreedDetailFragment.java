package com.unimib.App4ZampeAndroid.Views;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breeddetail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imagePetDetail = view.findViewById(R.id.imagePetDetail);
        TextView namePetDetail = view.findViewById(R.id.namePetDetail);
        TextView temperamentPetDetail = view.findViewById(R.id.temperamentPetDetail);
        TextView lifespanPetDetail = view.findViewById(R.id.lifespanPetDetail);
        TextView originPetDetail = view.findViewById(R.id.originPetDetail);
        TextView weightPetDetail = view.findViewById(R.id.weightPetDetail);
        TextView heightPetDetail = view.findViewById(R.id.heightPetDetail);
        TextView wikiPetDetail = view.findViewById(R.id.wikiPetDetail);

        if(b.getImage() != null){
            Picasso.get().load(b.getImage().getUrl()).into(imagePetDetail);
        }
        else {
            Picasso.get().load(R.drawable.placeholder_immagine).into(imagePetDetail);
        }
        namePetDetail.setText(b.getName());
        temperamentPetDetail.setText(b.getTemperament());
        lifespanPetDetail.setText(b.getLife_span());

        //controllo origine
        if(b.getOrigin()!= null) {
            originPetDetail.setText(b.getOrigin());
        }else{
            originPetDetail.setText("Non disponibile...");
        }

        if(b.getOrigin().equals("")){
            originPetDetail.setText("Non disponibile...");
        }

        //controllo wiki
        if(b.getWikipedia_url()!= null){
            wikiPetDetail.setText(b.getWikipedia_url());
        }else{
           wikiPetDetail.setText("Non disponibile...");
        }


        if(b.getWeight() != null){
            weightPetDetail.setText(b.getWeight().getMetric()+" kg");
        }else {
            weightPetDetail.setText("Non disponibile...");
        }
        if(b.getHeight() != null){
            heightPetDetail.setText(b.getHeight().getMetric()+" cm");
        }else {
            heightPetDetail.setText("Non disponibile...");
        }


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();


        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();

        item.setVisible(false);
        searchView.setVisibility(View.GONE);

    }


}