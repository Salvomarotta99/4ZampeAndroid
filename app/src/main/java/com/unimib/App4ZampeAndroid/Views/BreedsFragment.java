package com.unimib.App4ZampeAndroid.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unimib.App4ZampeAndroid.Adapters.BreedsAdapter;
import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class BreedsFragment extends Fragment {

    public static final String TAG = "BreedsFragment";
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
        InputStream fileInputStream = null;
        JsonReader jsonReader = null;
        try {
                fileInputStream = getActivity().getAssets().open("breed_list.json");
                jsonReader = new JsonReader(new InputStreamReader(fileInputStream, "UTF-8"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        Type collectionType = new TypeToken<List<Breed>>(){}.getType();
        List<Breed> lcs = (List<Breed>) new Gson()
                .fromJson( bufferedReader , collectionType);

        for (int i = 0; i < lcs.size(); i++)
        {
            Log.d(TAG, lcs.get(i).getName()+", "+lcs.get(i).getTemperament());
        }

        BreedsAdapter breedsAdapter = new BreedsAdapter(lcs, new BreedsAdapter.OnItemClickListener() {
            @Override
            public void onClick(Breed b) {
                Toast.makeText(getActivity(), b.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        breed_list.setLayoutManager(new LinearLayoutManager(getContext()));
        breed_list.setAdapter(breedsAdapter);

    }
}