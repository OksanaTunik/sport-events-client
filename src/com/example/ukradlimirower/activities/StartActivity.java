package com.example.ukradlimirower.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import com.example.ukradlimirower.R;
import com.example.ukradlimirower.com.example.api.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Oksana on 01.12.2014.
 */
public class StartActivity extends BaseActivity {
    Button btnSignUp;
    Button btnSignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);

        new RestoreSessionTask().execute(readApiKey());
    }

    protected void showAsUsual() {
        setContentView(R.layout.start_page);
        btnSignUp = (Button) this.findViewById(R.id.btnSignUp);
        btnSignIn = (Button) this.findViewById(R.id.btnSingIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(StartActivity.this,
                        SignUpActivity.class);

                startActivity(i);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(StartActivity.this,
                        SignInActivity.class);

                startActivity(i);
            }
        });
    }

    public class RestoreSessionTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return com.example.ukradlimirower.com.example.api.AccountApiClient.checkLogIn(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showNewEvent();
            } else {
                showAsUsual();
            }
        }
    }
}