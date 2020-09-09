package com.gralliams.gads2020leaderboard;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

public class SubmitActivity extends AppCompatActivity {

    private static final String BASE_FORM_URL = "https://docs.google.com/forms/d/e/";
    private EditText mEmail;
    private EditText mGitLink;
    private EditText mName;
    private EditText mSurname;
    private Retrofit mRetrofit;
    private String mUserName;
    private String mUserEmail;
    private String mUserGitLink;
    private String mUserSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);
        setSupportActionBar(toolbar);


        findViewById(R.id.submit_project_button).setOnClickListener(view -> sendProjectInfo());


    }

    private void sendProjectInfo() {
        mEmail = findViewById(R.id.edittext_email);
        mGitLink = findViewById(R.id.edittext_github_link);
        mName = findViewById(R.id.edittext_name);
        mSurname = findViewById(R.id.edittext_surname);

        mUserName = mName.getText().toString();
        mUserEmail = mEmail.getText().toString();
        mUserGitLink = mGitLink.getText().toString();
        mUserSurname = mSurname.getText().toString();

        if (!mUserEmail.trim().isEmpty() && !mUserGitLink.trim().isEmpty() && !mUserName.trim().isEmpty()
                && !mUserSurname.trim().isEmpty()) {
            showPermissionDialog();
        } else {
            Toast.makeText(this, "Please input text", Toast.LENGTH_SHORT).show();
        }
    }

    private void showResponse() {
        LeaderboardApiInterface leaderboardApiInterface = createRetrofitFormInstance().create(LeaderboardApiInterface.class);
        Call<Void> postCall = leaderboardApiInterface.submitProject(mUserEmail, mUserName, mUserSurname, mUserGitLink);
        postCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                clearfields();
                if (response.isSuccessful()) {
                    Toast.makeText(SubmitActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    // showSuccessDialog();
                    Log.v("TAG", response.message() + response.code());
                } else {
                    Toast.makeText(SubmitActivity.this, "failed! " + response.message() + " " +
                                    response.code(),
                            Toast.LENGTH_SHORT).show();
                    //showFailureDialog();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "Network request failed " + t.toString());
                Toast.makeText(SubmitActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showPermissionDialog() {
        final Dialog alertDialog = new Dialog(SubmitActivity.this);
        alertDialog.setContentView(R.layout.dialog_permission);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView cancelPermission = alertDialog.findViewById(R.id.imageView_no);
        Button givePermission = alertDialog.findViewById(R.id.button_yes);
        //TO DO IMPLEMENT IN ON START
        alertDialog.show();
        cancelPermission.setOnClickListener(view -> {
            //implement in onStop
            alertDialog.dismiss();
        });

        givePermission.setOnClickListener(view -> showResponse());
    }

    private void showFailureDialog() {
    }

    private void showSuccessDialog() {

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);


    }
}