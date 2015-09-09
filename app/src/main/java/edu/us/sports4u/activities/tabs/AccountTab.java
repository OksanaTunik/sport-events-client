package edu.us.sports4u.activities.tabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import edu.us.sports4u.R;
import edu.us.sports4u.activities.ChipsMultiAutoCompleteEditText;
import edu.us.sports4u.activities.autorization.LogInActivity;
import edu.us.sports4u.api.AccountApiClient;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.entities.UserAccount;

import java.io.*;
import java.util.List;

import static edu.us.sports4u.api.BaseActivity.*;

public class AccountTab extends Fragment {
    // new class level members
    private ImageView mDrawerImage;
    private TextView mDrawerText;
    private ListView mDrawerListView;
    protected List<Bitmap> imagesToUpload;
    protected Uri outputFileUri;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect;
    ImageView ivImage;
    ImageButton btnAddImage;
    EditText txtName;
    EditText txtAddress;

    ChipsMultiAutoCompleteEditText mu;

    // change the View inflation and extract the new child views
    // also modifying the ListView to now be a child instead of the root View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.account_tab, container, false);

        setHasOptionsMenu(true); //the options appear in your Toolbar

       // Button btnDone = (Button) fragmentView.findViewById(R.id.btnDone);

        txtName = (EditText) fragmentView.findViewById(R.id.txtName);
        txtAddress = (EditText) fragmentView.findViewById(R.id.txtAddress);

        txtName.setText(getUserAccount().getName());
        txtAddress.setText(getUserAccount().getAddress());

       // Button signOutBtn = (Button) fragmentView.findViewById(R.id.btnSignout);

        mu = (ChipsMultiAutoCompleteEditText) fragmentView.findViewById(R.id.multiAutoCompleteTextView1);

        String[] item = getResources().getStringArray(R.array.sports);

        mu.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, item));
        mu.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

       /* btnDone.setOnClickListener(new View.OnClickListener() {
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

            }
        });*/

       /* signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountTab.this.signOut();
            }
        });*/

        btnAddImage = (ImageButton) fragmentView.findViewById(R.id.btnAddImage);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });
       // ivImage = (ImageView) fragmentView.findViewById(R.id.ivImage);

        return fragmentView;
    }

    protected void signOut() {
        clearApiKey();
        Intent activity = new Intent(getActivity(), LogInActivity.class);
        startActivity(activity);
    }

    protected int getIntentId() {
        return 2;
    }

    // menu action bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                return true;

            case R.id.sign_out:
                AccountTab.this.signOut();
                return true;

            case R.id.menu_save:
                String name = txtName.getText().toString();
                String address = txtAddress.getText().toString();

                UserAccount newAccount = new UserAccount();
                newAccount.setName(name);
                newAccount.setAddress(address);
                updateAccount(newAccount);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        btnAddImage.setImageBitmap(bm);
    }

    private void updateAccount(UserAccount newAccount) {
        setUserAccount(newAccount);
        AccountApiClient.update(newAccount);
        new UpdateUserAccountTask().execute(newAccount);
    }

    public class UpdateUserAccountTask extends AsyncTask<UserAccount, Void, Boolean> {
        @Override
        protected Boolean doInBackground(UserAccount... params) {
            return AccountApiClient.update(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "Could not update your account", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
