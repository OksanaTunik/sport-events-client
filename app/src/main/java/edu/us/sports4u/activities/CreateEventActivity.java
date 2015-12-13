package edu.us.sports4u.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.text.DateFormat;
import java.util.Calendar;
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
    TextView lblDateAndTime;
    TextView tvTime;
    TextView tvDate;
    ImageButton btnDate;
    ImageButton btnTime;
    Date startsAt;

    private static final int BACK_FROM_LOCATION_CHOOSING = 1;
    private static final int BACK_FROM_SPECIFYING_DAYS = 2;

    //Date and Time
    DateFormat fmtDateAndTime = DateFormat.getTimeInstance();
    DateFormat fmtDate = DateFormat.getDateInstance();
    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startsAt = myCalendar.getTime();
            updateDate();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            startsAt = myCalendar.getTime();
            updateTime();
        }
    };

    private void updateDate() {
        tvDate.setText(fmtDate.format(myCalendar.getTime()));
    }

    private void updateTime() {
        tvTime.setText(fmtDateAndTime.format(myCalendar.getTime()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        Intent intent = getIntent();
        String sport = intent.getStringExtra("SportName");
        txtSport = (EditText) findViewById(R.id.txtSport);
        txtSport.setText(sport);
        txtSport.addTextChangedListener(new MyTextWatcher());

        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        locationBtn = (ImageButton) findViewById(R.id.imgLocation);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventActivity.this, ChooseLocationOnMap.class);

                startActivityForResult(intent, BACK_FROM_LOCATION_CHOOSING);
            }
        });

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btnDate = (ImageButton) findViewById(R.id.imgbtnDay);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(CreateEventActivity.this, d, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnTime = (ImageButton) findViewById(R.id.imgbtnClock);
        btnTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new TimePickerDialog(CreateEventActivity.this, t, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar
                        .get(Calendar.MINUTE), true).show();
            }
        });

        updateTime();
        updateDate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            txtAddress.setText(data.getExtras().getString("address"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_event_menu, menu);

        return true;
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

                Event event = new Event(title, description, address, sport, startsAt);
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
            if (res)
                showTabs();
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

