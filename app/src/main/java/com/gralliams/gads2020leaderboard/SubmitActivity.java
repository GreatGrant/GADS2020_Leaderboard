package com.gralliams.gads2020leaderboard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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
    private Dialog mFailureDialog;
    private Dialog mSuccessDialog;
    private Dialog mPermissionDialog;

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
                    mPermissionDialog.dismiss();
                    showSuccessDialog();
                    Log.v("TAG", response.message() + response.code());
                } else {
//                    Toast.makeText(SubmitActivity.this, "failed! " + response.message() + " " +
//                                    response.code(),
//                            Toast.LENGTH_SHORT).show();
                    mPermissionDialog.dismiss();
                    showFailureDialog();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "Network request failed " + t.toString());
                mPermissionDialog.dismiss();
                showFailureDialog();
                Toast.makeText(SubmitActivity.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void showPermissionDialog() {
        mPermissionDialog = new Dialog(SubmitActivity.this);
        mPermissionDialog.setContentView(R.layout.dialog_permission);
        Objects.requireNonNull(mPermissionDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView cancelPermission = mPermissionDialog.findViewById(R.id.imageView_no);
        Button givePermission = mPermissionDialog.findViewById(R.id.button_yes);
        mPermissionDialog.show();
        cancelPermission.setOnClickListener(view -> {
            mPermissionDialog.dismiss();
        });

        givePermission.setOnClickListener(view -> showResponse());
    }

    private void showFailureDialog() {
        Dialog failureDialog = new Dialog(SubmitActivity.this);
        failureDialog.setContentView(R.layout.dialog_failure);
        Objects.requireNonNull(failureDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        failureDialog.show();
        failureDialog.setCanceledOnTouchOutside(true);

    }

    private void showSuccessDialog() {
        Dialog successDialog = new Dialog(SubmitActivity.this);
        successDialog.setContentView(R.layout.dialog_success);
        Objects.requireNonNull(successDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.show();
        successDialog.setCanceledOnTouchOutside(true);


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