package edu.us.sports4u.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.api.EventsApiClient;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.R;
import edu.us.sports4u.entities.ListEventsParams;

import java.util.ArrayList;
import java.util.List;

public class ListEventActivity extends BaseActivity {
    ListView lvMain = null;

    EventAdapter adapter;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.events_tab);

        lvMain = (ListView) findViewById(R.id.lvMain);

        registerForContextMenu(lvMain);
       // Button btnDone = (Button) findViewById(R.id.btnDone);

        lvMain.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long id) {

                String event = (String) view.getTag();
                Toast.makeText(getBaseContext(), event, Toast.LENGTH_LONG)
                        .show();

                showEvent();

            }
        });

//        List<String> sports = new ArrayList<String>();
//        sports.add("fff");
        ListEventsParams params = new ListEventsParams();
//        params.lat = 3.14f;
//        params.lng = 5.f;
        params.radius = 10000.f;
//        params.sports = sports;
        new ListEventsTask().execute(params);
    }

        private void showEvent() {
            Intent intent = new Intent(this, DetailEventActivity.class);
            startActivity(intent);
        }

    private void bindEvents(List<Event> events) {
        adapter = new EventAdapter(this, R.id.lvMain,
                new ArrayList<Event>(events));

        lvMain.setAdapter(adapter);
    }

    private void saveEvent(Event event) {

        adapter.add(event);
        adapter.notifyDataSetChanged();
    }

    private void updateEvent(Event event) {
    Event item = adapter.getEventById(event.getId());
        item.setTitle(event.getTitle());
        adapter.notifyDataSetChanged();
    }

    class EventAdapter extends ArrayAdapter<Event> {
        Context ctx;
        LayoutInflater lInflater;
        ArrayList<Event> events;
        EventAdapter(Context context, int textViewResourceId,
                        ArrayList<Event> events) {

            super(context, textViewResourceId, events);

            ctx = context;
            this.events = events;
            lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void remove(Event event) {
            events.remove(event);
            events.trimToSize();
        }

        public void add(Event event) {
            events.add(event);
        }

        public Event getEventById(String id)
        {
            for(Event element : events)
            {
                if(element.getId().equals(id))
                {
                    return element;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Event getItem(int position) {
            return events.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.events_list_item, parent, false);
            }

            Event event = getItem(position);
            ((TextView) view.findViewById(R.id.title)).setText(event
                    .getTitle());
            ((TextView) view.findViewById(R.id.description)).setText(event
                    .getDescription());

            view.setTag(event.getId());

            return view;
        }
    }

    class ListEventsTask extends AsyncTask<ListEventsParams, Void, List<Event>> {

        @Override
        protected void onPreExecute() {

        };

        @Override
        protected void onPostExecute(List<Event> result) {
            // binding
            bindEvents(result);
        };

        @Override
        protected List<Event> doInBackground(ListEventsParams... params) {
            List<Event> events = new ArrayList<Event>();

            try {
                return EventsApiClient.getEvents(ListEventActivity.this.readApiKey(), params[0].query, params[0].address, params[0].radius, params[0].sports);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return events;
        }

    }

    public void showEditEvent(Event event) {

        Intent intent = new Intent(this, EditEventActivity.class);
  //      intent.putExtra("NOTE_ID", note.getId());
        startActivity(intent);

    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        Event selectedItem = (Event) adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(getBaseContext(), "edit", Toast.LENGTH_LONG).show();

                showEditEvent(selectedItem);

                return true;
            case R.id.delete:
                Toast.makeText(getBaseContext(), "delete", Toast.LENGTH_LONG)
                        .show();

           //     new DeleteEventTask().execute(token, selectedItem.getId());

                adapter.remove(selectedItem);
                adapter.notifyDataSetChanged();

                return true;
            default:
                return super.onContextItemSelected((android.view.MenuItem) item);
        }
    }

}

   /* ListView lvMain = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);

        new LoadLostAlertsTask().execute();
    }

    public class LoadLostAlertsTask extends AsyncTask<Void, Void, List<LostAlert>> {
        @Override
        protected List<LostAlert> doInBackground(Void... voids) {
            return EventsApiClient.getAllLostAlerts();
        }

        @Override
        protected void onPostExecute(List<LostAlert> result) {
            setContentView(R.layout.lost_alerts_list);

            ListView lvMain = (ListView) findViewById(R.id.lvMain);

            LostAlertAdapter adapter = new LostAlertAdapter(EventListActivity.this, R.layout.lost_alerts_list_item, result);

            lvMain.setAdapter(adapter);

            lvMain.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String alertId = (String) view.getTag();
                    showLostAlert(alertId);
                }
            });

            Button btnAlert = (Button) findViewById(R.id.btnAlert);

            btnAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNewLostAlert();
                }
            });
        }
    }

    public class LostAlertAdapter extends ArrayAdapter<LostAlert> {
        public LostAlertAdapter(Context context, int resource) {
            super(context, resource);
        }

        public LostAlertAdapter(Context context, int resource, List<LostAlert> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItem = convertView;

            if (listItem == null) {
                listItem = getLayoutInflater().inflate(R.layout.lost_alerts_list_item, parent, false);
            }

            LostAlert alert = getItem(position);

            if (alert == null) {
                return listItem;
            }

            // ImageView image = (ImageView) listItem.findViewById(R.id.image);
            // image.setImageResource(...);

            TextView title = (TextView) listItem.findViewById(R.id.title);
            TextView description = (TextView) listItem.findViewById(R.id.description);
            ImageView image = (ImageView) listItem.findViewById(R.id.image);

            title.setText(alert.getTitle());
            description.setText(alert.getDescription());

            if (alert.images.size() > 0)
                image.setImageBitmap(alert.bitmaps.get(0));

            listItem.setTag(String.format("%d", alert.getId()));

            return listItem;
        }
    }*/

