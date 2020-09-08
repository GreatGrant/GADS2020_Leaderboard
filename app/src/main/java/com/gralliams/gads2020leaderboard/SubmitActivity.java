package com.gralliams.gads2020leaderboard;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubmitActivity extends AppCompatActivity {

    private static final String BASE_FORM_URL = "https://docs.google.com/forms/d/e/";
    private EditText mEmail;
    private EditText mGitLink;
    private EditText mName;
    private EditText mSurname;
    private Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.submit_project_button).setOnClickListener(view -> sendProjectInfo());


    }

    private void sendProjectInfo() {
        mEmail = findViewById(R.id.edittext_email);
        mGitLink = findViewById(R.id.edittext_github_link);
        mName = findViewById(R.id.edittext_name);
        mSurname = findViewById(R.id.edittext_surname);

        String userName = mName.getText().toString();
        String userEmail = mEmail.getText().toString();
        String userGitLink = mGitLink.getText().toString();
        String userSurname = mSurname.getText().toString();

        if (!userEmail.trim().isEmpty() && !userGitLink.trim().isEmpty() && !userName.trim().isEmpty()
                && !userSurname.trim().isEmpty()) {

            LeaderboardApiInterface leaderboardApiInterface = createRetrofitFormInstance().create(LeaderboardApiInterface.class);
            Call<Void> postCall = leaderboardApiInterface.submitProject(userEmail, userName, userSurname, userGitLink);
            postCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    clearfields();
                    if (response.isSuccessful()) {

                        Toast.makeText(SubmitActivity.this, "Success! " + response.message(), Toast.LENGTH_SHORT).show();
                        Log.v("TAG", response.message() + response.code());
                    } else {
                        Toast.makeText(SubmitActivity.this, "failed! " + response.message() + " " +
                                        response.code(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("TAG", "Network request failed " + t.toString());
                    Toast.makeText(SubmitActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please input text", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearfields() {
        mEmail.setText("");
        mGitLink.setText("");
        mName.setText("");
        mSurname.setText("");
    }

    private Retrofit createRetrofitFormInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().
                    baseUrl(BASE_FORM_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return mRetrofit;
    }
}