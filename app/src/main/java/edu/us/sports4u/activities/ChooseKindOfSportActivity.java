package edu.us.sports4u.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import edu.us.sports4u.R;

import java.util.ArrayList;

/**
 * Created by Oksana on 03.01.2015.
 */
public class ChooseKindOfSportActivity extends Activity {

    private ImageAdapter mAdapter;
    private ArrayList<String> listTasks;
    private ArrayList<Integer> listImageTasks;
    public String mystring;

    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests_gridview);

        prepareList();

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new ImageAdapter(this,listTasks, listImageTasks);

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {

                Intent intent = new Intent(ChooseKindOfSportActivity.this, CreateEventActivity.class);

                String nameOfSport=mAdapter.getItem(position);
                intent.putExtra("SportName", nameOfSport);
                startActivity(intent);
            }
        });

        gridView.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }

        });

    }

    public void prepareList()
    {
        listTasks = new ArrayList<String>();

        listTasks.add("Running");
        listTasks.add("Swimming");
        listTasks.add("Volleyball");
        listTasks.add("Skiing");
        listTasks.add("Snowboard");
        listTasks.add("Football");
        listTasks.add("Table tennis");
        listTasks.add("Tennis");
        listTasks.add("Climbing");
        listTasks.add("Cycling");
        listTasks.add("Weight Lifter");
        listTasks.add("Basketball");
        listTasks.add("Skating");

        listImageTasks = new ArrayList<Integer>();
        listImageTasks.add(R.drawable.sport_running);
        listImageTasks.add(R.drawable.sport_ski);
        listImageTasks.add(R.drawable.sport_skiing);
        listImageTasks.add(R.drawable.sport_snowboard);
        listImageTasks.add(R.drawable.sport_swimming);
        listImageTasks.add(R.drawable.sport_tabletennis);
        listImageTasks.add(R.drawable.sport_tennis);
        listImageTasks.add(R.drawable.sport_volleyball);
        listImageTasks.add(R.drawable.sport_weightlifting);
        listImageTasks.add(R.drawable.sport_football);
        listImageTasks.add(R.drawable.sport_climbing);
        listImageTasks.add(R.drawable.sport_bicycle);
        listImageTasks.add(R.drawable.sport_basketball);
    }
}
