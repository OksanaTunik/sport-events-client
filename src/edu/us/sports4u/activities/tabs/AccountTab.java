package edu.us.sports4u.activities.tabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;
import edu.us.sports4u.R;
import edu.us.sports4u.activities.autorization.LogInActivity;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.entities.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountTab extends Fragment {
    // new class level members
    private ImageView mDrawerImage;
    private TextView mDrawerText;
    private ListView mDrawerListView;
    protected List<Bitmap> imagesToUpload;
    protected Uri outputFileUri;

    // change the View inflation and extract the new child views
    // also modifying the ListView to now be a child instead of the root View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.account_tab, container, false);

        // now grab the separate child views from inside it
        //mDrawerListView = (ListView) fragmentView.findViewById(R.id.nav_listView);
        //mDrawerImage = (ImageView) fragmentView.findViewById(R.id.nav_image);
        //mDrawerText = (TextView) fragmentView.findViewById(R.id.nav_text);

        imagesToUpload = new ArrayList<Bitmap>();

        Button btnDone = (Button) fragmentView.findViewById(R.id.btnDone);
        ImageButton btnAddImage = (ImageButton) fragmentView.findViewById(R.id.btnAddImage);
        Button signOutBtn = (Button) fragmentView.findViewById(R.id.btnSignout);

//        btnAddImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            //    openImageIntent();
//            }
//        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtName = (EditText) fragmentView.findViewById(R.id.txtName);
                EditText txtAddress = (EditText) fragmentView.findViewById(R.id.txtAddress);

                String name = txtName.getText().toString();
                String address = txtAddress.getText().toString();

                UserAccount newAccount = new UserAccount();
                newAccount.setName(name);
                newAccount.setAddress(address);
                BaseActivity.setUserAccount(newAccount);

//                Bundle bundle = getIntent().getExtras();
//                String alertId = bundle.getString("alertId");

//                setContentView(R.layout.waiting);
//                new CreateFoundAlertTask(readApiKey(), alertId, title, description, lat, lon).execute();*/
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountTab.this.signOut();
            }
        });

        return fragmentView;
    }

    protected void signOut() {
        BaseActivity.clearApiKey();
        Intent activity = new Intent(getActivity(), LogInActivity.class);
        startActivity(activity);
    }

    protected int getIntentId() {
        return 2;
    }

   /* protected void openImageIntent() {
        try {
            // Determine Uri of camera image to save.
            final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "ukradli-mi-rower-data" + File.separator);
            root.mkdirs();
            //final String fname = "image.jpg";
            final File sdImageMainDirectory = File.createTempFile("camimg", "jpg", root);
            outputFileUri = Uri.fromFile(sdImageMainDirectory);

            // Camera.
            final List<Intent> cameraIntents = new ArrayList<Intent>();
            final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            final PackageManager packageManager = getPackageManager();
            final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
            for (ResolveInfo res : listCam) {
                final String packageName = res.activityInfo.packageName;
                final Intent intent = new Intent(captureIntent);
                intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                intent.setPackage(packageName);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                cameraIntents.add(intent);
            }

            // Filesystem.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // Chooser of filesystem options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

            startActivityForResult(chooserIntent, getIntentId());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }*/
}
