package com.mymodule.utils;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mymodule.R;


/**
 * Created by wscubetech on 2/11/15.
 */
public class ToolbarOperation {

    AppCompatActivity act;

    public ToolbarOperation(AppCompatActivity act) {
        this.act = act;
    }

    public void setupToolbar(String title, Boolean backstatus) {
        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        act.setSupportActionBar(toolbar);
        act.getSupportActionBar().setDisplayShowHomeEnabled(true);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(backstatus);
        act.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

    }

}
