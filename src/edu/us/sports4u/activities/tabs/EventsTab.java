package edu.us.sports4u.activities.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;
import edu.us.sports4u.R;
import edu.us.sports4u.activities.DetailEventActivity;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.api.EventsApiClient;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.entities.ListEventsParams;

import java.util.ArrayList;
import java.util.List;

public class EventsTab extends Fragment {
    protected EventAdapter adapter;
    protected ListView lvMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.events_tab, container, false);

        lvMain = (ListView) fragmentView.findViewById(R.id.lvMain);

        registerForContextMenu(lvMain);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long id) {
                String eventId = (String) view.getTag();
                Toast.makeText(getActivity().getBaseContext(), eventId, Toast.LENGTH_LONG)
                        .show();

                showEvent();
            }
        });

        EditText etSearchQuery = (EditText) fragmentView.findViewById(R.id.findEventsQuery);

        ListEventsParams params = new ListEventsParams();
        params.address = BaseActivity.getUserAccount().getAddress();
        //params.radius = 10000.f;
        params.query = etSearchQuery.getText().toString();
        //params.addSport("");

        new ListEventsTask().execute(params);

        return fragmentView;
    }

    private void showEvent() {
        Intent intent = new Intent(getActivity(), DetailEventActivity.class);
        startActivity(intent);
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

        public Event getEventById(String id) {
            for (Event element : events) {
                if (element.getId().equals(id)) {
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

    private void bindEvents(List<Event> events) {
        adapter = new EventAdapter(getActivity(), R.id.lvMain,
                new ArrayList<Event>(events));

        lvMain.setAdapter(adapter);
    }

    class ListEventsTask extends AsyncTask<ListEventsParams, Void, List<Event>> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(List<Event> result) {
            // binding
            bindEvents(result);
        }

        @Override
        protected List<Event> doInBackground(ListEventsParams... params) {
            List<Event> events = new ArrayList<Event>();

            try {
                return EventsApiClient.getEvents(BaseActivity.readApiKey(), params[0].query, params[0].address, params[0].radius, params[0].sports);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return events;
        }
    }

    class DeleteEventsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }
}