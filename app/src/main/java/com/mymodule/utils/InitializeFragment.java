package com.mymodule.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mymodule.R;

/**
 * Created by wscubetech on 2/11/15.
 */
public class InitializeFragment {

    Activity act;
    String addOrReplace;
    Context con;


    public InitializeFragment(Activity act, String addOrReplace) {
        this.act = act;
        this.addOrReplace = addOrReplace;
    }

    public InitializeFragment(Context con, String addOrReplace) {
        this.con = con;
        this.addOrReplace = addOrReplace;
    }


    public void initFragment(Fragment fragment, boolean addToBackStack, FragmentManager manager) {
        if (fragment != null) {
            FragmentManager fragmentManager = manager;

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (addOrReplace.equalsIgnoreCase("add"))
                fragmentTransaction.add(R.id.frameLayout, fragment);
            else
                fragmentTransaction.replace(R.id.frameLayout, fragment);

           /* if (fragmentManager.getFragments().contains(fragment)) {
                fragmentManager.popBackStack();
            }*/

            if (addToBackStack)
                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
