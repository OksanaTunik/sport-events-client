package com.example.ukradlimirower.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.example.ukradlimirower.R;
import com.example.ukradlimirower.com.example.api.AccountApiClient;
import com.example.ukradlimirower.com.example.api.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonalPageActivity extends BaseActivity {
    protected List<Bitmap> imagesToUpload;

    @Override
    protected int getIntentId() {
        return 3;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        imagesToUpload = new ArrayList<Bitmap>();

        Button btnAddImg = (Button) findViewById(R.id.btnAddImage);
        Button btnDone = (Button) findViewById(R.id.btnDone);

        btnAddImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageIntent();
            }
        });

        btnDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
          //      EditText txtName = (EditText) findViewById(R.id.txtText);
              //  EditText txtCity = (EditText) findViewById(R.id.txtText);

        /*        if (txtBicycle.getError() == null && txtText.getError() == null) {

                    String title = txtBicycle.getText().toString();
                    String description = txtText.getText().toString();

                    setContentView(R.layout.waiting);

                    new CreateEventTask().execute(title, description);
                }*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK || requestCode != getIntentId()) {
            return;
        }

        final boolean isCamera;

        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();

            if (action == null) {
                isCamera = false;
            } else {
                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }

        Uri selectedImageUri;

        if (isCamera) {
            selectedImageUri = outputFileUri;
        } else {
            selectedImageUri = (data == null) ? null : data.getData();
        }

        Bitmap bmp = null;

        try {
            bmp = BitmapFactory.decodeStream(new java.net.URL(selectedImageUri.toString()).openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(this);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new AbsListView.LayoutParams(200, 200));
        LinearLayout imageList = (LinearLayout) findViewById(R.id.images);
        imageView.setImageBitmap(bmp);
        imageList.addView(imageView, 1);

        imagesToUpload.add(bmp);
    }

 /*   public class CreateEventTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String apiKey = readApiKey();
         //   return AccountApiClient.addEvent(apiKey, params[0], params[1], params[2], params[3]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
              //  showLostAlertsList();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                showSignUp();
            }
        }
    }*/
}
