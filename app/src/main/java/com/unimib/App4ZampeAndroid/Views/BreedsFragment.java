package com.unimib.App4ZampeAndroid.Views;

import android.app.ActionBar;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unimib.App4ZampeAndroid.Adapters.BreedsAdapter;
import com.unimib.App4ZampeAndroid.MainActivity;
import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.Models.ImageBreed;
import com.unimib.App4ZampeAndroid.R;
import com.unimib.App4ZampeAndroid.Repositories.BreedsCallback;
import com.unimib.App4ZampeAndroid.Repositories.BreedsRepository;
import com.unimib.App4ZampeAndroid.ViewModels.BreedsViewModel;
import com.unimib.App4ZampeAndroid.ViewModels.BreedsViewModelFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class BreedsFragment extends Fragment {

    public static final String TAG = "BreedsFragment";

    private BreedsRepository breedsRepository;
    private List<Breed> breedList;
    private List<Breed> breedListAll;
    private ImageBreed imageBreed;
    private BreedsAdapter breedsAdapter;
    private long lastUpdate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true); // Add this!

        breedsRepository = new BreedsRepository(requireActivity().getApplication(), lastUpdate);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        ProgressBar loader = getView().findViewById(R.id.loading);
        RecyclerView breed_list = view.findViewById(R.id.breed_list);
        TextView is_connected = getView().findViewById(R.id.is_connected);
        TextView refresh = getView().findViewById(R.id.refresh);

        if(!isConnected)
        {
            is_connected.setVisibility(view.VISIBLE);
            breed_list.setVisibility(view.GONE);
            refresh.setVisibility(view.VISIBLE);

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment selectedFragment = new PagerBreedsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout,
                                    selectedFragment).commit();
                }
            });
        }

        else {

            is_connected.setVisibility(view.GONE);
            refresh.setVisibility(view.GONE);
            loader.setVisibility(view.VISIBLE);
            breed_list.setVisibility(view.GONE);

            breedList = new ArrayList<>();
            // Creation of a ViewModel using NewsViewModelFactory to pass other parameters
            // to the constructor of NewsViewModel
            BreedsViewModel breedsCatViewModel = new ViewModelProvider(getActivity(), new BreedsViewModelFactory(
                    requireActivity().getApplication(), breedsRepository)).get(BreedsViewModel.class);

            // Creation of a ViewModel using NewsViewModelFactory to pass other parameters
            // to the constructor of NewsViewModel
            BreedsViewModel breedsDogViewModel = new ViewModelProvider(this, new BreedsViewModelFactory(
                    requireActivity().getApplication(), breedsRepository)).get(BreedsViewModel.class);

        /*TabLayout tabLayout = view.findViewById(R.id.tabView);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getPosition());
                switch(tab.getPosition()){
                    case 0:

                    case 1:
                        Fragment selectedFragment = new BreedsCatsFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_layout,
                                        selectedFragment).commit();
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/

            loader.setVisibility(view.VISIBLE);
            breed_list.setVisibility(view.GONE);

            breedList = new ArrayList<>();
            breedsAdapter = new BreedsAdapter(breedList, new BreedsAdapter.OnItemClickListener() {
                @Override
                public void onClick(Breed b) {
                    Fragment selectedFragment = new BreedDetailFragment(b, getActivity());
                    Toast.makeText(getActivity(), b.getName(), Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout,
                                    selectedFragment).commit();
                }

            });
            breed_list.setLayoutManager(new GridLayoutManager(getContext(), 2));
            breed_list.setAdapter(breedsAdapter);


            // Short version to link ViewModel and LiveData
            breedsDogViewModel.getBreedsListDog().observe(getViewLifecycleOwner(), response -> {
                // Update the UI
                if (response != null) {
                    if (response.size() != -1) {
                        updateDogUIsuccess(response);
                    } else {
                        updateUIfailure("Ciao");
                    }
                }
            });

        }
    }


   private void updateDogUIsuccess(List<Breed> breedList) {
       this.breedList.addAll(breedList);
       requireActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               breedsAdapter.notifyDataSetChanged();
           }
       });
        this.getView().findViewById(R.id.loading).setVisibility(getView().GONE);
        this.getView().findViewById(R.id.breed_list).setVisibility(getView().VISIBLE);
    }

    private void updateUIfailure(String msg) {
        this.getView().findViewById(R.id.loading).setVisibility(getView().GONE);
        this.getView().findViewById(R.id.breed_list).setVisibility(getView().VISIBLE);
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();


        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(breedsAdapter != null)
                breedsAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }




}