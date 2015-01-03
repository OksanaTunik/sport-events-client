package com.example.ukradlimirower;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oksana on 03.12.2014.
 */
public class ImageAdapter extends BaseAdapter {

        private ArrayList<String> listTasks;
        private ArrayList<Integer> listImageTasks;
        private Activity activity;

        public ImageAdapter(Activity activity,ArrayList<String> listTasks, ArrayList<Integer> listImageTasks) {
            super();
            this.listTasks = listTasks;
            this.listImageTasks = listImageTasks;
            this.activity = activity;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return listTasks.size();
        }

        public String getItem(int position) {
            // TODO Auto-generated method stub
            return listTasks.get(position);
        }
        public Integer getItemImage(int position) {
            // TODO Auto-generated method stub
            return listImageTasks.get(position);
        }


        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public static class ViewHolder
        {
            public ImageView imgViewFlag;
            public TextView txtViewTitle;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder view;
            LayoutInflater inflator = activity.getLayoutInflater();

            if(convertView==null)
            {
                view = new ViewHolder();
                convertView = inflator.inflate(R.layout.interests_gridview_row, null);

                view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
                view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);

                convertView.setTag(view);
            }
            else
            {
                view = (ViewHolder) convertView.getTag();
            }

            view.txtViewTitle.setText(listTasks.get(position));
            view.imgViewFlag.setImageResource(listImageTasks.get(position));

            return convertView;

        }




}
