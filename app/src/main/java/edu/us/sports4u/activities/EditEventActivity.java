package edu.us.sports4u.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import edu.us.sports4u.R;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.api.EventsApiClient;
import edu.us.sports4u.entities.Event;

import java.util.Date;

/**
 * Created by shybovycha on 23.11.14.
 */
public class  EditEventActivity extends BaseActivity {
        EditText txtTitle;
        EditText txtDescription;
        EditText txtAddress;
        EditText txtSport;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.create_event);

            Intent intent = getIntent();
            String sport = intent.getStringExtra("SportName");
            txtSport = (EditText) findViewById(R.id.txtSport);
            Toast.makeText(EditEventActivity.this, sport, Toast.LENGTH_SHORT).show();
            txtSport.setText(sport);
            txtSport.addTextChangedListener(new MyTextWatcher());

            txtTitle = (EditText) findViewById(R.id.txtTitle);
            txtDescription = (EditText) findViewById(R.id.txtDescription);
            txtAddress = (EditText) findViewById(R.id.txtAddress);
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
                    return EventsApiClient.createEvent(EditEventActivity.this.readApiKey(), params[0]);
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


   /* protected List<Bitmap> imagesToUpload;

    protected int getIntentId() {
        return 2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        imagesToUpload = new ArrayList<Bitmap>();

        Button btnDone = (Button) findViewById(R.id.btnDone);
        Button btnAddImage = (Button) findViewById(R.id.btnAddImage);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageIntent();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
                EditText txtDescription = (EditText) findViewById(R.id.txtDescription);

                String title = "Found"; // txtTitle.getText().toString();
                String description = txtDescription.getText().toString();

                double lat = getLat();
                double lon = getLon();

                Bundle bundle = getIntent().getExtras();
                String alertId = bundle.getString("alertId");

                setContentView(R.layout.waiting);

           //     new CreateFoundAlertTask(readApiKey(), alertId, title, description, lat, lon).execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK || requestCode != getIntentId()) {
            return;
        }

        final boolean isCamera;

        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();

            if (action == null) {
                isCamera = false;
            } else {
                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }

        Uri selectedImageUri;

        if (isCamera) {
            selectedImageUri = outputFileUri;
        } else {
            selectedImageUri = (data == null) ? null : data.getData();
        }

        Bitmap bmp = null;

        try {
            bmp = BitmapFactory.decodeStream(new java.net.URL(selectedImageUri.toString()).openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(this);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new AbsListView.LayoutParams(200, 200));
        LinearLayout imageList = (LinearLayout) findViewById(R.id.images);
        imageView.setImageBitmap(bmp);
        imageList.addView(imageView, 1);

        imagesToUpload.add(bmp);
    }

 /*   public class CreateEventTask extends AsyncTask<Void, Void, Boolean> {
        protected String apiKey;
        protected String title;
        protected String description;
        protected String alertId;
        protected Double lat;
        protected Double lon;

        public CreateFoundAlertTask(String apiKey, String alertId, String title, String description, Double lat, Double lon) {
            super();

            this.apiKey = apiKey;
            this.alertId = alertId;
            this.title = title;
            this.description = description;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return EventsApiClient.createFoundAlert(apiKey, alertId, title, description, lat, lon, imagesToUpload);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            showLostAlert(alertId);
        }
    }*/
