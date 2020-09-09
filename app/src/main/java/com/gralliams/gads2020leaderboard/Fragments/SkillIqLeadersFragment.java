package com.gralliams.gads2020leaderboard.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gralliams.gads2020leaderboard.ApiClient;
import com.gralliams.gads2020leaderboard.LeaderboardApiInterface;
import com.gralliams.gads2020leaderboard.Models.SkillIqLeaders;
import com.gralliams.gads2020leaderboard.R;
import com.gralliams.gads2020leaderboard.RecyclerAdapters.SkillIqRecyclerAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillIqLeadersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillIqLeadersFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mResponse;
    private ProgressBar mProgressBar;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public SkillIqLeadersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SkillIqLeadersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillIqLeadersFragment newInstance() {
        SkillIqLeadersFragment fragment = new SkillIqLeadersFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skill_iq_leaders, container, false);
        mResponse = view.findViewById(R.id.skilliq_response_textView);
        mProgressBar = view.findViewById(R.id.skill_progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        prepareRecyclerLayout(view);
        getApiResponse();
        return view;
    }

    private void getApiResponse() {
        LeaderboardApiInterface leaderboardApiInterface = ApiClient.getClient().create(LeaderboardApiInterface.class);
        Call<List<SkillIqLeaders>> skillLearners = leaderboardApiInterface.getSkillLearners();
        skillLearners.enqueue(new Callback<List<SkillIqLeaders>>() {
            @Override
            public void onResponse(Call<List<SkillIqLeaders>> call, Response<List<SkillIqLeaders>> response) {
                List<SkillIqLeaders> skillIqLeaders= response.body();
                mRecyclerView.setAdapter(new SkillIqRecyclerAdapter(getContext(), skillIqLeaders));
                List<SkillIqLeaders> skillList = response.body();
                mRecyclerView.setAdapter(new SkillIqRecyclerAdapter(getContext(), skillList));
                mProgressBar.setVisibility(View.INVISIBLE);
                mResponse.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<SkillIqLeaders>> call, Throwable t) {
                Log.e("TAG", "Response: "+t.toString());
                mResponse.setVisibility(View.VISIBLE);
                mResponse.setText(R.string.connection_error_message);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void prepareRecyclerLayout(View view) {
        mRecyclerView = view.findViewById(R.id.skill_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
    }
}