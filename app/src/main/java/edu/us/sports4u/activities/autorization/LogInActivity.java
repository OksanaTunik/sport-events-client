package edu.us.sports4u.activities.autorization;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import edu.us.sports4u.api.AccountApiClient;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.R;
import edu.us.sports4u.entities.UserAccount;
import org.json.JSONObject;

public class LogInActivity extends BaseActivity {
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnSignup;

    LoginButton btnFacebook;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.login);

        callbackManager = CallbackManager.Factory.create();

        btnFacebook = (LoginButton) findViewById(R.id.btnFacebook);
        btnFacebook.setReadPermissions("email,public_profile,user_location");

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                Profile user = profile2;

                // user logged out, nothing to do
                if (user == null)
                    return;

                // `id` and `name` are taken from Profile
                // `email` and `address` are taken from GraphAPI

                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObj, GraphResponse response) {
                                try {
                                    JSONObject locationObj = jsonObj.getJSONObject("location");

                                    String facebookId = jsonObj.getString("id");
                                    String name = jsonObj.getString("name");
                                    String email = jsonObj.getString("email");
                                    String address = locationObj.getString("name");

                                    BaseActivity.getUserAccount().setAddress(address);
                                    LogInActivity.this.facebookSignIn(facebookId, email, name);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle graphRequestParams = new Bundle();
                graphRequestParams.putString("fields", "id,name,email,location");

                graphRequest.setParameters(graphRequestParams);
                graphRequest.executeAsync();
            }
        };

        profileTracker.startTracking();

        txtUserName = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPwd);

        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        btnSignup = (Button) this.findViewById(R.id.btnSignup);

        txtUserName.requestFocus();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
            new FetchUserAccountTask().execute(readApiKey());
        }
    }
}
