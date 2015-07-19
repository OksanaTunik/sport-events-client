package edu.us.sports4u.activities.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.us.sports4u.R;
import edu.us.sports4u.activities.DetailEventActivity;
import edu.us.sports4u.entities.Event;

import java.util.ArrayList;

public class CalendarTab extends Fragment {
    protected EventAdapter adapter;
    protected ListView lvCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.calendar_tab, container, false);

        lvCalendar = (ListView) fragmentView.findViewById(R.id.lvCalendar);

        registerForContextMenu(lvCalendar);

        lvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long id) {

                String eventId = (String) view.getTag();
                Toast.makeText(getActivity().getBaseContext(), eventId, Toast.LENGTH_LONG)
                        .show();

                showCalendar();

            }
        });

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
}
