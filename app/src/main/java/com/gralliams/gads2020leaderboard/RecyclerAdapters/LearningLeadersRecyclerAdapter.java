package com.gralliams.gads2020leaderboard.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gralliams.gads2020leaderboard.Models.LearningLeaders;
import com.gralliams.gads2020leaderboard.R;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LearningLeadersRecyclerAdapter extends  RecyclerView.Adapter<LearningLeadersRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<LearningLeaders>mLearningLeadersList;

    public LearningLeadersRecyclerAdapter(Context context, List<LearningLeaders> learningLeadersList) {
        mLearningLeadersList = learningLeadersList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate
                (R.layout.learning_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String learningLeadersInfo =
                mLearningLeadersList.get(position).getHours() +
                        " learning hours, " +
                        mLearningLeadersList.get(position).getCountry();

        holder.learningLeader.setText(mLearningLeadersList.get(position).getName());
        holder.learningLeaderInfo.setText(learningLeadersInfo);
    }

    @Override
    public int getItemCount() {
        return mLearningLeadersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView learningLeader;
        TextView learningLeaderInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            learningLeader = itemView.findViewById(R.id.tv_learning_leaders);
            learningLeaderInfo = itemView.findViewById(R.id.tv_learning_leaders_info);
        }
    }
}