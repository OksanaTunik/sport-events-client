package edu.us.sports4u.activities.maps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.us.sports4u.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ChooseLocationOnMap extends FragmentActivity implements LocationListener, OnMapClickListener {
    private SupportMapFragment mapFragment;
    //private LocationClient mLocationClient;
    double latitude;
    double longitude;
    Marker selectedLocation;
    LatLng latLng;
    MarkerOptions markerOptions;
    String latLongString = "Unknown";
    String addressString = "No address found";
    private LocationTuple point;
    private LocationTuple currentpoint;
    String addressText;
    GoogleMap mMap;
    Marker marker;
    TextView address;

    Geocoder geocoder;
    List<Address> addresses;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private final String TAG = "MyAwesomeApp";

    private TextView mLocationView;

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    public static FragmentManager fragmentManager;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        setContentView(R.layout.google_map);
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = supportMapFragment.getMap();

        address = (TextView) findViewById(R.id.address);

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng coordinate = new LatLng(lat, lng);

        if (location != null) {
            onLocationChanged(location);
        }

        //locationManager.requestLocationUpdates(bestProvider, 20000, 0, (android.location.LocationListener) this);
        CameraUpdate center = CameraUpdateFactory.newLatLng(coordinate);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

        //create initial marker
        marker = googleMap.addMarker(new MarkerOptions()
                .position(coordinate)
                .title("Location")
                .snippet("First Marker"));

        marker.showInfoWindow();
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        //googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);

        try {
            geocoder = new Geocoder(ChooseLocationOnMap.this, Locale.ENGLISH);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            StringBuilder str = new StringBuilder();
            if (geocoder.isPresent()) {
                Toast.makeText(getApplicationContext(),
                        "geocoder present", Toast.LENGTH_SHORT).show();
                Address returnAddress = addresses.get(0);

                String localityString = returnAddress.getLocality();
                String city = returnAddress.getCountryName();
                String street=returnAddress.getSubAdminArea();

                str.append(localityString + " ");
                str.append(city + "" + street + "");

                address.setText(str);
                Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(),
                        "geocoder not present", Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block

            Log.e("tag", e.getMessage());
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        marker.setPosition(latLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        latLng = new LatLng(latitude, longitude);
        //googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);

        try {
            geocoder = new Geocoder(ChooseLocationOnMap.this, Locale.ENGLISH);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            StringBuilder str = new StringBuilder();
            if (geocoder.isPresent()) {
                Toast.makeText(getApplicationContext(),
                        "geocoder present", Toast.LENGTH_SHORT).show();
                Address returnAddress = addresses.get(0);

                String localityString = returnAddress.getLocality();
                String city = returnAddress.getCountryName();
                String street=returnAddress.getSubAdminArea();

                str.append(localityString + " ");
                str.append(city + "" + street + "");

                address.setText(str);
                Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(),
                        "geocoder not present", Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block

            Log.e("tag", e.getMessage());
        }
    }

}