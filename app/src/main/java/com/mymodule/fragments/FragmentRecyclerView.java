package com.mymodule.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mymodule.R;
import com.mymodule.activities.DashboardActivity;
import com.mymodule.activities.LoginActivity;
import com.mymodule.adapters.AdapterMatch;
import com.mymodule.customviews.MyFragment;
import com.mymodule.models.MatchListModel;
import com.mymodule.models.MyDetail;
import com.mymodule.mysharedprefrence.MyPrefData;
import com.mymodule.utils.CheckNetwork;
import com.mymodule.utils.MyDialog;
import com.mymodule.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FragmentRecyclerView extends MyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RecyclerView recyclerView;
    AdapterMatch adapterMatch;

    ArrayList<MatchListModel> arrayOfMatches = new ArrayList<>();

    public FragmentRecyclerView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        v.setOnClickListener(this);
        init(v);

        if (new CheckNetwork().isConnected(getActivity())) {
            getMatchList();
        } else {
            new MyDialog(getActivity()).getNoInternetDialog().show();
        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        DashboardActivity.mToolbar.setTitle("");
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void getMatchList() {
        arrayOfMatches.clear();
        final Dialog progress = new MyDialog(getActivity()).getProgressDialog();
        progress.show();

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Token", new MyPrefData(getActivity()).getMyDetails().getToken())
                .build();

        final Request request = new Request.Builder().url(Urls.getMatchList)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Failure", "" + e);
                String response = getString(R.string.networkError);
                handleResponse(response, progress);
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                if (res.isSuccessful()) {
                    String response = Html.fromHtml(res.body().string()).toString();
                    Log.v("ResponsePostSuccess", response);
                    handleResponse(response, progress);
                }
            }
        });
    }

    public void handleResponse(final String response, final Dialog progress) {
        DashboardActivity.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (response.equalsIgnoreCase(getString(R.string.networkError))) {
                        new MyDialog(getActivity()).getNoInternetDialog().show();
                    } else {
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getBoolean("Success")) {
                                JSONArray array = json.getJSONArray("match");

                                Type listType = new TypeToken<ArrayList<MatchListModel>>() {
                                }.getType();

                                arrayOfMatches = new GsonBuilder().create().fromJson(array.toString(), listType);
                            }
                        } catch (Exception e) {
                            if (progress.isShowing())
                                progress.dismiss();
                            Log.v("ParsingException", "" + e);
                        }
                    }
                    if (progress.isShowing())
                        progress.dismiss();

                    Log.v("Size", "" + arrayOfMatches.size());

                    recyclerView.setAdapter(new AdapterMatch(getActivity(), arrayOfMatches));

                } catch (Exception e) {
                    if (progress.isShowing())
                        progress.dismiss();
                    Log.v("Exception", "" + e);
                }
            }
        });
    }


}
