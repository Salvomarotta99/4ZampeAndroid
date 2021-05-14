package com.unimib.App4ZampeAndroid.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unimib.App4ZampeAndroid.Adapters.BreedsAdapter;
import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.R;

import java.util.ArrayList;
import java.util.List;


public class BreedsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //ListView breed_list = view.findViewById(R.id.breed_list);
        RecyclerView breed_list = view.findViewById(R.id.breed_list);

        List<Breed> breedList = new ArrayList<Breed>();

        for (int i = 0; i < 10; i++)
        {
            breedList.add(new Breed("00"+i,"razza"+i,"qqqq","wwww","eeee","dddd","ddd","ssss","sd","ddsds"));
        }

        //BreedsAdapter breedsAdapter = new BreedsAdapter(breedList, getActivity());

        /*breed_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), breedList.get(position).getAlt_names(), Toast.LENGTH_LONG).show();
            }
        });*/


        BreedsAdapter breedsAdapter = new BreedsAdapter(breedList, new BreedsAdapter.OnItemClickListener() {
            @Override
            public void onClick(Breed b) {
                Toast.makeText(getActivity(), b.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        breed_list.setLayoutManager(new LinearLayoutManager(getContext()));
        breed_list.setAdapter(breedsAdapter);


    }
}