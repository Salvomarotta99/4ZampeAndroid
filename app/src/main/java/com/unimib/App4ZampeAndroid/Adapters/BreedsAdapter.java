package com.unimib.App4ZampeAndroid.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.R;

import java.util.List;

public class BreedsAdapter extends BaseAdapter {

    private List<Breed> breedList;
    private Activity activity;

    public BreedsAdapter(List<Breed> breedList, Activity activity) {
        this.breedList = breedList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return breedList.size();
    }

    @Override
    public Breed getItem(int position) {
        return breedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.breed_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.breed_name))
                .setText(getItem(position).getName());
        return convertView;
    }

}
