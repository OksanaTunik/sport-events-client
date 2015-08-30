package edu.us.sports4u.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import edu.us.sports4u.R;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.entities.EventsStorage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oksana on 01.01.2015.
 */
public class DetailEventActivity extends BaseActivity {
    TextView tvTitle;
    TextView tvDescription;
    TextView tvAddress;
    TextView tvSport;
    ImageButton btnJoin;
    ImageButton btnLeave;
    TextView tvTime;
    TextView tvDate;
    String eventId = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
      //  Event event = new Event(getTaskId(),getTitle());

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvAddress = (TextView) findViewById(R.id.tvlocation);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDate = (TextView) findViewById(R.id.tvDate);
        btnJoin = (ImageButton) findViewById(R.id.btnJoin);
        btnLeave = (ImageButton) findViewById(R.id.btnLeave);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailEventActivity.this.joinEvent();
            }
        });

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailEventActivity.this.leaveEvent();
            }
        });

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");

        populateData(EventsStorage.getEventById(eventId));
    }

    private void switchJoinButton() {
        if (isEventJoined()) {
            btnJoin.setVisibility(View.INVISIBLE);
            btnLeave.setVisibility(View.VISIBLE);
        } else {
            btnJoin.setVisibility(View.VISIBLE);
            btnLeave.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isEventJoined() {
        return getUserAccount().getEventIds().contains(this.eventId);
    }

    private void joinEvent() {
        super.joinEvent(this.eventId, readApiKey());
        switchJoinButton();
    }

    private void leaveEvent() {
        super.leaveEvent(this.eventId, readApiKey());
        switchJoinButton();
    }

    private void populateData(Event event)
    {
        Date startsAt = event.getStartsAt();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String startsAtStr = dateFormat.format(startsAt);

        tvTitle.setText(event.getTitle());
        tvDate.setText(startsAtStr);
        tvDescription.setText(event.getDescription());
        tvAddress.setText(event.getAddress());
    }

    public void showEventEdit(String eventId)
    {
        Intent intent = new Intent(this,EditEventActivity.class);
        intent.putExtra("EVENT_ID", eventId);
        startActivity(intent);
    }

    // menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                showEventEdit(eventId);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

