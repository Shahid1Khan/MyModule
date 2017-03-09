package com.mymodule.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mymodule.R;
import com.mymodule.models.MatchListModel;

import java.util.ArrayList;

public class AdapterMatch extends RecyclerView.Adapter<AdapterMatch.ViewHolder> {

    Activity activity;
    ArrayList<MatchListModel> arrayOfData = new ArrayList<>();

    public AdapterMatch(Activity activity, ArrayList<MatchListModel> arrayOfData) {
        this.activity = activity;
        this.arrayOfData = arrayOfData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_matches, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MatchListModel data = arrayOfData.get(position);

        holder.txtTitle.setText(data.getMatchName());
        holder.txtTime.setText(data.getMatchSchedule());
        holder.txtTeam1.setText(data.getTeam1Name());
        holder.txtTeam2.setText(data.getTeam2Name());


        String flag1 = data.getTeam1Flag();
        String flag2 = data.getTeam2Flag();

        try {
            Glide.with(activity).load(flag1).thumbnail(0.1f).placeholder(R.drawable.dummy).into(holder.imgFlag1);
            Glide.with(activity).load(flag2).thumbnail(0.1f).placeholder(R.drawable.dummy).into(holder.imgFlag2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayOfData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFlag1, imgFlag2;
        TextView txtTitle, txtTime,txtTeam1, txtTeam2;

        LinearLayout relMainRow;

        public ViewHolder(View v) {
            super(v);

            imgFlag1 = (ImageView) v.findViewById(R.id.imgFlag1);
            imgFlag2 = (ImageView) v.findViewById(R.id.imgFlag2);

            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
            txtTeam1 = (TextView) v.findViewById(R.id.txtTeam1);
            txtTeam2 = (TextView) v.findViewById(R.id.txtTeam2);
        }
    }
}
