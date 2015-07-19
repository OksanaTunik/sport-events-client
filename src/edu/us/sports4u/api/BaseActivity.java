package edu.us.sports4u.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import edu.us.sports4u.activities.*;
import edu.us.sports4u.activities.autorization.LogInActivity;
import edu.us.sports4u.activities.autorization.SignUpActivity;

public abstract class BaseActivity extends Activity {
    protected static String apiKey;

    public void showLogin() {
        Intent intent = getIntent(LogInActivity.class);
        startActivity(intent);
    }

    public void showSignup() {
        Intent intent = getIntent(SignUpActivity.class, true);
        startActivity(intent);
    }

    public void showNewEvent() {
        Intent intent = getIntent(CreateEventActivity.class);
        startActivity(intent);
    }

    public void showEventList() {
        Intent intent = getIntent(ListEventActivity.class);
        startActivity(intent);
    }

    public void chooseKindOfSportActivity()
    {
        Intent intent = new Intent(this, ChooseKindOfSportActivity.class);
        startActivity(intent);
    }

    public void showTabs()
    {
        Intent intent = new Intent(this, MainTabActivity.class);
        startActivity(intent);
    }

    public double getLon() {
        if (getLocation() == null) {
            return 0.0;
        } else {
            return getLocation().getLongitude();
        }
    }

    /*protected Location getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return location;
    }*/

    private Location getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = lm.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }

            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                //ALog.d("found best last known location: %s", l);
                bestLocation = l;
            }
        }

        if (bestLocation == null) {
            return null;
        }

        return bestLocation;
    }

    public static Bitmap getBitmapFromUrl(String url) {
        Bitmap res = null;

        try {
            URL ulrn = new URL(BaseApiClient.publicUrl + url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            res = BitmapFactory.decodeStream(is);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    protected Intent getIntent(Class goTo) {
        return getIntent(goTo, false);
    }

    protected Intent getIntent(Class goTo, boolean allowGoBack) {
        Intent intent = new Intent(this, goTo);

        if (!allowGoBack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        return intent;
    }

    protected static File getApiKeyFile() {
        File root = new File(Environment.getExternalStorageDirectory() + File.separator + "ukradli-mi-rower-data" + File.separator);
        root.mkdirs();
        String fname = "APIKEY";

        return new File(root, fname);
    }

    public static void storeApiKey(String apiKey) {
        File file = getApiKeyFile();
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(file);
            fos.write(apiKey.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearApiKey() {
        File file = getApiKeyFile();

        file.delete();
    }

    public static String readApiKey() {
        return null;

//        if (apiKey != null) {
//            return apiKey;
//        }
//
//        File file = getApiKeyFile();
//
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            Scanner reader = new Scanner(fis);
//            apiKey = reader.next();
//            fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return apiKey;
    }
}
