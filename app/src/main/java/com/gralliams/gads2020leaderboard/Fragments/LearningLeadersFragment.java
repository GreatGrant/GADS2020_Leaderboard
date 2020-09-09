package com.gralliams.gads2020leaderboard.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gralliams.gads2020leaderboard.ApiClient;
import com.gralliams.gads2020leaderboard.LeaderboardApiInterface;
import com.gralliams.gads2020leaderboard.Models.LearningLeaders;
import com.gralliams.gads2020leaderboard.R;
import com.gralliams.gads2020leaderboard.RecyclerAdapters.LearningLeadersRecyclerAdapter;

import java.util.List;
//import com.gralliams.gadsleaderboard2020.RecyclerAdapters.LearningLeadersRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearningLeadersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearningLeadersFragment extends Fragment {
    private static final String BASE_URL = "https://gadsapi.herokuapp.com";
    private static LeaderboardApiInterface mLeaderboardApiInterface;
    private Retrofit mRetrofit;
    private static RecyclerView mRecyclerView;
    private TextView mResponse;
    private ProgressBar mProgressBar;
    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public LearningLeadersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LearningLeadersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearningLeadersFragment newInstance() {
        LearningLeadersFragment fragment = new LearningLeadersFragment();
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
        View view = inflater.inflate(R.layout.fragment_learning_leaders, container, false);
        mProgressBar = view.findViewById(R.id.learning_progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        prepareRecyclerLayout(view);
        getApiResponse();
        mResponse = view.findViewById(R.id.learning_leaders_response_textview);
        return view;
    }

    private void getApiResponse() {
        mLeaderboardApiInterface = ApiClient.getClient().create(LeaderboardApiInterface.class);
        Call<List<LearningLeaders>> learningLeadersCall = mLeaderboardApiInterface.getLearningLeaders();

        learningLeadersCall.enqueue(new Callback<List<LearningLeaders>>() {
            @Override
            public void onResponse(Call<List<LearningLeaders>> call, Response<List<LearningLeaders>> response) {
                List<LearningLeaders> learningList = response.body();
//                LearningLeadersFragment.
                mRecyclerView.setAdapter(new LearningLeadersRecyclerAdapter(getContext(), learningList));
                mProgressBar.setVisibility(View.INVISIBLE);
                mResponse.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<LearningLeaders>> call, Throwable t) {
                Log.d("TAG", "Response = "+t.toString());
                mResponse.setVisibility(View.VISIBLE);
                mResponse.setText(R.string.connection_error_message);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void prepareRecyclerLayout(View view) {
        mRecyclerView = view.findViewById(R.id.learning_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setHasFixedSize(true);
    }
}