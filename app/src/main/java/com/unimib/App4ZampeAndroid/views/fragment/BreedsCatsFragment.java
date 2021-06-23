package com.unimib.App4ZampeAndroid.views.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.App4ZampeAndroid.adapters.BreedsAdapter;
import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.Models.ImageBreed;
import com.unimib.App4ZampeAndroid.R;
import com.unimib.App4ZampeAndroid.repositories.BreedsRepository;
import com.unimib.App4ZampeAndroid.viewmodels.BreedsViewModel;
import com.unimib.App4ZampeAndroid.viewmodels.BreedsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class BreedsCatsFragment extends Fragment {

    public static final String TAG = "BreedsCatsFragment";

    private BreedsRepository breedsRepository;
    private List<Breed> breedList;
    private List<Breed> breedListAll;
    private ImageBreed imageBreed;
    private BreedsAdapter breedsAdapter;
    private long lastUpdate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true); // Add this!
        breedsRepository = new BreedsRepository(requireActivity().getApplication(), lastUpdate);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cats_breeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        ProgressBar loader = getView().findViewById(R.id.loadingCat);
        RecyclerView breed_list = view.findViewById(R.id.breed_list_cat);
        TextView is_connected = getView().findViewById(R.id.is_connected_cat);
        TextView refresh = getView().findViewById(R.id.refresh_cat);

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
        } else{

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


           /* TabLayout tabLayout = view.findViewById(R.id.tabView);
            tabLayout.selectTab(tabLayout.getTabAt(1));


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    System.out.println(tab.getPosition());
                    switch(tab.getPosition()){
                        case 0:
                            Fragment selectedFragment = new BreedsFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_layout,
                                            selectedFragment).commit();
                        case 1:

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
            breed_list.setLayoutManager(new GridLayoutManager(getContext(),2));
            breed_list.setAdapter(breedsAdapter);


            // Short version to link ViewModel and LiveData
            breedsCatViewModel.getBreedsListCat().observe(getViewLifecycleOwner(), response -> {
                // Update the UI
                if (response != null) {
                    if (response.size() != -1) {
                        updateCatUIsuccess(response);
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
        this.getView().findViewById(R.id.loadingCat).setVisibility(getView().GONE);
        this.getView().findViewById(R.id.breed_list_cat).setVisibility(getView().VISIBLE);
    }

    private void updateCatUIsuccess(List<Breed> breedList) {
        this.breedList.addAll(breedList);
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                breedsAdapter.notifyDataSetChanged();
            }
        });
        this.getView().findViewById(R.id.loadingCat).setVisibility(getView().GONE);
        this.getView().findViewById(R.id.breed_list_cat).setVisibility(getView().VISIBLE);
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