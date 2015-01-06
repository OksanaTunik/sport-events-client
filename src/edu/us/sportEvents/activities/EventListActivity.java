package edu.us.sportEvents.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import edu.us.sportEvents.api.BaseActivity;
import edu.us.sportEvents.entities.Event;
import com.example.ukradlimirower.R;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends BaseActivity {
    ListView lvMain = null;

    EventAdapter adapter;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.events);

        lvMain = (ListView) findViewById(R.id.lvMain);

        registerForContextMenu(lvMain);

        lvMain.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long id) {

                String notebookId = (String) view.getTag();
                Toast.makeText(getBaseContext(), notebookId, Toast.LENGTH_LONG)
                        .show();

                showEvent();

            }
        });
        new EventsTask().execute();
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

    class NewEventTask extends AsyncTask<Event, Void, Event> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Event event) {
            saveEvent(event);
        }

        @Override
        protected Event doInBackground(Event... params) {
            Event notebook = null;
            try {
           //     notebook = Storage.Current().Notebook().createNotebook(params[0]);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return notebook;
        }
    }


    class UpdateEventTask extends AsyncTask<Event, Void, Event> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Event event) {
            updateEvent(event);
        }

        @Override
        protected Event doInBackground(Event... params) {
            Event event = null;
            try {
              //  event = Storage.Current().Notebook().updateNotebook(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return event;
        }
    }

    class DeleteEventTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                //Storage.Current().Notebook().deleteNotebook(params[1]);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }


    class EventsTask extends AsyncTask<String, Void, List<Event>> {

        @Override
        protected void onPreExecute() {

        };

        @Override
        protected void onPostExecute(List<Event> result) {
            // binding
            bindEvents(result);
        };

        @Override
        protected List<Event> doInBackground(String... params) {
            List<Event> notebooks = new ArrayList<Event>();

            try {
           //     notebooks = Storage.Current().Notebook().getNotebooks();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return notebooks;
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

