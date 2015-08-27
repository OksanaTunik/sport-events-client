package edu.us.sports4u.activities.autorization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.R;
import org.json.JSONObject;

public class SignUpActivity extends BaseActivity {
    EditText txtEmail;
    EditText txtPassword;
    EditText txtDispName;
    Button btnSignup;
    Button btnLogin;

    LoginButton btnFacebook;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.signup);

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
                                    SignUpActivity.this.facebookSignIn(facebookId, email, name);
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

        txtEmail = (EditText) this.findViewById(R.id.txtEmail);
        txtDispName = (EditText) this.findViewById(R.id.txtDisplayName);
        txtPassword = (EditText) this.findViewById(R.id.txtPwd);
        btnSignup = (Button) this.findViewById(R.id.btnSignup);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);

        txtEmail.requestFocus();

        btnSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.signUp();
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.showLogin();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signUp() {
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

            new SignUpTask().execute(email, password, name);
        }
    }
}
