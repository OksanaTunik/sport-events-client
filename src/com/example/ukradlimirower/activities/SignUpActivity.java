package com.example.ukradlimirower.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ukradlimirower.R;
import com.example.ukradlimirower.com.example.api.BaseActivity;

public class SignUpActivity extends BaseActivity implements OnClickListener {
    EditText txtEmail;
    EditText txtPassword;
    EditText txtDispName;
    Button btnSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        txtEmail = (EditText) this.findViewById(R.id.txtEmail);
        txtDispName = (EditText) this.findViewById(R.id.txtDisplayName);
        txtPassword = (EditText) this.findViewById(R.id.txtPwd);
        btnSignup = (Button) this.findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtPassword = (EditText) findViewById(R.id.txtPwd);
        EditText txtDispName = (EditText) findViewById(R.id.txtDisplayName);

        if (txtEmail.getText().length() == 0) {
            txtEmail.setError("please enter the email");

        } else if (!isEmailValid(txtEmail.getText())) {
            txtEmail.setError("email is not Valid");
        }

        if (txtDispName.getText().length() == 0 && txtDispName.getText().length() < 255) {
            txtDispName.setError("please enter the display name");
        }

        if (txtPassword.getText().length() == 0) {
            txtPassword.setError("please enter the password");
        }

        if (txtEmail.getError() == null && txtDispName.getError() == null && txtPassword.getError() == null) {
            String name = txtDispName.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();

            setContentView(R.layout.waiting);

            new SignupTask().execute(email, password, name);
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public class SignupTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return com.example.ukradlimirower.com.example.api.AccountApiClient.signUp(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                storeApiKey(result);
                chooseKindOfSportActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong username/password", Toast.LENGTH_SHORT).show();
                showSignUp();
            }
        }
    }
}
