package edu.us.sports4u.activities.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.us.sports4u.R;
import edu.us.sports4u.activities.DetailEventActivity;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.api.EventsApiClient;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.entities.ListEventsParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CalendarTab extends Fragment {
    protected EventAdapter adapter;
    protected ListView lvCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.calendar_tab, container, false);

        lvCalendar = (ListView) fragmentView.findViewById(R.id.lvCalendar);

        registerForContextMenu(lvCalendar);

        lvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                String eventId = (String) view.getTag();

                showCalendar();
            }
        });

        ListEventsParams params = new ListEventsParams();
        params.address = BaseActivity.getUserAccount().getAddress();
        //params.radius = 10000.f;
        params.addSports(BaseActivity.getUserAccount().getSportFavorites());

        new ListEventsTask().execute(params);

        return fragmentView;
    }

    private void showCalendar() {
        Intent intent = new Intent(getActivity(), DetailEventActivity.class);
        startActivity(intent);
    }

    private void updateEvent(Event event) {
        Event item = adapter.getEventById(event.getId());
        item.setTitle(event.getTitle());
        adapter.notifyDataSetChanged();
    }

    private void bindEvents(List<Event> events) {
        adapter = new EventAdapter(getActivity(), R.id.lvCalendar, new ArrayList<Event>(events));
        lvCalendar.setAdapter(adapter);
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
            lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                view = lInflater.inflate(R.layout.calendar_list_item, parent, false);
            }

            Event event = getItem(position);

            ((TextView) view.findViewById(R.id.title)).setText(event.getTitle());
            ((TextView) view.findViewById(R.id.participants)).setText(event.getDescription());

            if (event.getGroup() != null) {
                TextView groupTitle = (TextView) view.findViewById(R.id.groupTitle);
                groupTitle.setVisibility(View.VISIBLE);
                groupTitle.setText(event.getGroup());
            }

            String startsAt = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(event.getStartsAt());
            ((TextView) view.findViewById(R.id.startsAt)).setText(startsAt);

            view.setTag(event.getId());

            return view;
        }
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
                return EventsApiClient.getCalendarEvents(BaseActivity.readApiKey(), params[0].address, params[0].radius, params[0].sports);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return events;
        }
    }
}
