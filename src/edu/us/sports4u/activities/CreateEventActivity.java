package edu.us.sports4u.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import edu.us.sports4u.activities.maps.ChooseLocationOnMap;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.api.EventsApiClient;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.R;

import java.util.Date;

/**
 * Created by shybovycha on 23.11.14.
 */
public class CreateEventActivity extends BaseActivity {
        EditText txtTitle;
        EditText txtDescription;
        EditText txtAddress;
        EditText txtSport;
        ImageButton locationBtn;

    private static final int BACK_FROM_LOCATION_CHOOSING = 1;
    private static final int BACK_FROM_SPECIFYING_DAYS = 2;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.create_event);

            Intent intent = getIntent();
            String sport = intent.getStringExtra("SportName");
            txtSport = (EditText) findViewById(R.id.txtSport);
            Toast.makeText(CreateEventActivity.this, sport, Toast.LENGTH_SHORT).show();
            txtSport.setText(sport);
            txtSport.addTextChangedListener(new MyTextWatcher());

            txtTitle = (EditText) findViewById(R.id.txtTitle);
            txtDescription = (EditText) findViewById(R.id.txtDescription);
            txtAddress = (EditText) findViewById(R.id.txtAddress);
            locationBtn = (ImageButton) findViewById(R.id.locationBtn);

            locationBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateEventActivity.this, ChooseLocationOnMap.class);

                    startActivityForResult(intent, BACK_FROM_LOCATION_CHOOSING);
                }

            });

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.save_event_menu, menu);
            return true;

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
            }
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {

                case R.id.menu_save:
                    String title = txtTitle.getText().toString();
                    String description = txtDescription.getText().toString();
                    String address = txtAddress.getText().toString();
                    String sport = txtSport.getText().toString();

                    if (txtTitle.getText().length() == 0)
                        txtTitle.setError("please enter the title");

                    if (txtDescription.getText().length() == 0)
                        txtDescription.setError("please enter the description");

                    if (txtAddress.getText().length() == 0)
                        txtAddress.setError("please enter the address");

                    Event event = new Event(title, description, address, sport, new Date());
                    new SaveEventTask().execute(event);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        class SaveEventTask extends AsyncTask<Event, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean res) {
                if(res)
                    showEventList();

            }

            @Override
            protected Boolean doInBackground(Event... params) {
                try {
                    return EventsApiClient.createEvent(CreateEventActivity.this.readApiKey(), params[0]);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            }
        }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

