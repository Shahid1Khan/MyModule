package com.mymodule.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mymodule.R;
import com.mymodule.activities.DashboardActivity;
import com.mymodule.customviews.MyFragment;


public class FragmentGallery extends MyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ImageView imgDummy;

    public FragmentGallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        v.setOnClickListener(this);
        init(v);


        imgDummy=(ImageView) v.findViewById(R.id.imgDummy);

        String imgUrl = "http://hdwallpapershdpics.com/wp-content/uploads/2016/09/Dubai-Photos-Images-Travel-Tourist-Images-Pictures-800x600.jpg";
        try {
            Glide.with(getActivity()).load(imgUrl).thumbnail(0.2f).placeholder(R.drawable.dummy).into(imgDummy);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        DashboardActivity.mToolbar.setTitle("");
    }

    private void init(View view) {

    }
}
