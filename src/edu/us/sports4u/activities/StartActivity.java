package edu.us.sports4u.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import edu.us.sports4u.api.AccountApiClient;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.R;

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

    public class RestoreSessionTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return AccountApiClient.checkLogIn(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showTabs();
            } else {
                showSignup();
            }
        }
    }
}