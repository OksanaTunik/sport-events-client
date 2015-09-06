package edu.us.sports4u.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import edu.us.sports4u.R;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.entities.EventsStorage;
import edu.us.sports4u.entities.UserAccount;

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
    Button btnShare;
    TextView tvTime;
    TextView tvDay;
    TextView tvMonth;
    String eventId = null;
    Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.view_event);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvTime = (TextView) findViewById(R.id.tvStartsAtTime);
        tvDay = (TextView) findViewById(R.id.tvStartsAtDay);
        tvMonth = (TextView) findViewById(R.id.tvStartsAtMonth);
        btnJoin = (ImageButton) findViewById(R.id.btnJoin);
        btnLeave = (ImageButton) findViewById(R.id.btnLeave);
        btnShare = (Button) findViewById(R.id.btnShare);

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

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailEventActivity.this.shareViaFacebook();
            }
        });

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");

        event = EventsStorage.getEventById(eventId);

        populateData(event);

        //facebook
    }

    private void switchJoinButton() {
        if (isEventJoined()) {
            btnJoin.setVisibility(View.GONE);
            btnLeave.setVisibility(View.VISIBLE);
        } else {
            btnJoin.setVisibility(View.VISIBLE);
            btnLeave.setVisibility(View.GONE);
        }
    }

    private boolean isEventJoined() {
        return getUserAccount().getEventIds().contains(this.eventId);
    }

    private void joinEvent() {
        super.joinEvent(this.eventId, readApiKey());
        BaseActivity.getUserAccount().joinEvent(this.eventId);
        switchJoinButton();
    }

    private void leaveEvent() {
        super.leaveEvent(this.eventId, readApiKey());
        BaseActivity.getUserAccount().leaveEvent(this.eventId);
        switchJoinButton();
    }

    private void populateData(Event event)
    {
        Date startsAt = event.getStartsAt();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //String startsAtStr = dateFormat.format(startsAt);

        tvTitle.setText(event.getTitle());
        tvDay.setText(new SimpleDateFormat("dd").format(startsAt));
        tvMonth.setText(new SimpleDateFormat("MMM").format(startsAt));
        tvTime.setText(new SimpleDateFormat("HH:mm").format(startsAt));
        tvDescription.setText(event.getDescription());
        tvAddress.setText(event.getAddress());

        switchJoinButton();
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

    private void shareViaFacebook() {
        String link = "http://google.com/";
        String date = new SimpleDateFormat("dd MMM").format(event.getStartsAt());
        String description = String.format("I am going to %s on %s", event.getTitle(), date);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(link))
                .setContentDescription(description)
                .build();

        CallbackManager callbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("MOO", "SHARED SUCCESSFULLY");
            }

            @Override
            public void onCancel() {
                Log.d("MOO", "SHARE CANCELLED");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("MOO", "SHARE FAILED");
            }
        });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.show(content);
        }
    }
}
