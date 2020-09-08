package com.gralliams.gads2020leaderboard.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gralliams.gads2020leaderboard.Models.SkillIqLeaders;
import com.gralliams.gads2020leaderboard.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SkillIqRecyclerAdapter extends RecyclerView.Adapter<SkillIqRecyclerAdapter.ViewHolder>{
        private Context mContext;
        LayoutInflater mLayoutInflater;
        private List<SkillIqLeaders> mSkillIqLeadersList;

    public SkillIqRecyclerAdapter(Context context, List<SkillIqLeaders> skillIqLeadersList) {
        mLayoutInflater = LayoutInflater.from(context);
        mSkillIqLeadersList = skillIqLeadersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.skill_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String skillIqdata =
                mSkillIqLeadersList.get(position).getSkillIq() +
                        " skill IQ score, " +
                        mSkillIqLeadersList.get(position).getCountry();

        holder.skillIqLeader.setText(mSkillIqLeadersList.get(position).getName());
        holder.skillIqInfo.setText(skillIqdata);
    }

    @Override
    public int getItemCount() { return mSkillIqLeadersList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView skillIqLeader;
        TextView skillIqInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skillIqLeader = itemView.findViewById(R.id.tv_skill_iq_leader);
            skillIqInfo = itemView.findViewById(R.id.tv_skill_iq_info);
        }
    }
}