package edu.us.sports4u.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import edu.us.sports4u.api.AccountApiClient;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.R;

import java.util.logging.Logger;

/**
 * Created by Oksana on 01.12.2014.
 */
public class StartActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.waiting);

        if (readApiKey() != null) {
            new RestoreSessionTask().execute(readApiKey());
        } else {
            showSignup();
        }
    }

/*    @Override
    public void onStart() {
        super.onStart();

        if (!isNetworkAvailable()) {
            // Toast.makeText(getBaseContext(), "No internet connection", Toast.LENGTH_LONG).show();
            Log.e("MOO", "No internet connection");
        }
    }*/
}