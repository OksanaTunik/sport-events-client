package edu.us.sportEvents.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.us.sportEvents.R;

public class MessagesTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.messages_tab, container, false);
//        ((TextView)windows.findViewById(R.id.textView1)).setText("Windows");
        return fragmentView;
    }}