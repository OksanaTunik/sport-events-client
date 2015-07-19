package edu.us.sports4u.activities.autorization;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.us.sports4u.api.AccountApiClient;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.R;

public class LogInActivity extends BaseActivity {
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.waiting);

        setContentView(R.layout.login);

        txtUserName = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPwd);

        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        btnSignup = (Button) this.findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.logIn();
            }
        });

        btnSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.showSignup();
            }
        });
    }

    protected void logIn() {
        EditText txtUserName = (EditText) findViewById(R.id.txtEmail);
        EditText txtPassword = (EditText) findViewById(R.id.txtPwd);

        if (txtUserName.getText().length() == 0) {
            txtUserName.setError("please enter the email");

        } else if (!isEmailValid(txtUserName.getText())) {
            txtUserName.setError("email is not Valid");
        }

        if (txtPassword.getText().length() == 0) {
            txtPassword.setError("please enter the password");
        }

        if (txtUserName.getError() == null && txtPassword.getError() == null) {
            String username = txtUserName.getText().toString();
            String password = txtPassword.getText().toString();

            setContentView(R.layout.waiting);

            new SignInTask().execute(username, password);
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public class SignInTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return AccountApiClient.logIn(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                storeApiKey(result);
                showTabs();
//                chooseKindOfSportActivity();
//                showNewEvent();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong username/password", Toast.LENGTH_SHORT).show();
                showLogin();
            }
        }
    }
}
