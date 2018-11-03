package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private LayoutInflater inflater;
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();

    public NewsRecyclerViewAdapter(Context context,ArrayList<String> title, ArrayList<String> desc, ArrayList<String> date) {
        inflater= LayoutInflater.from(context);
        mTitles = title;
        mDescriptions = desc;
        mDate = date;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Log.e(TAG, " onBondViewHolder was called");
        holder.title.setText("Title: " + mTitles.get(position));
        holder.date.setText("Date: " + mDate.get(position));
        holder.description.setText("Description: " + mDescriptions.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.e("Toast", " I couldnt afford toast...so this will do");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView date;
        LinearLayout parentLayout;
        public NewsViewHolder (View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title_view);
            description = itemView.findViewById(R.id.desc_view);
            date = itemView.findViewById(R.id.date_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
