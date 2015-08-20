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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.us.sports4u.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class ChooseLocationOnMap extends FragmentActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener {


	private SupportMapFragment mapFragment;
	private GoogleMap googleMap;
	//private LocationClient mLocationClient;
	double latitude;
	double longitude;
	Marker selectedLocation;
	public List<Address> addresses = null;
	LatLng latLng;
	MarkerOptions markerOptions;
	String latLongString = "Unknown";
	String addressString = "No address found";
	private LocationTuple point;
	private LocationTuple currentpoint;
	String addressText;

	Geocoding gps;

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	private final String TAG = "MyAwesomeApp";

	private TextView mLocationView;

	private GoogleApiClient mGoogleApiClient;

	private LocationRequest mLocationRequest;
	public static FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLocationView = new TextView(this);

		setContentView(mLocationView);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.build();

		setContentView(R.layout.google_map);
		fragmentManager = getSupportFragmentManager();

		LocationManager locationManager;
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, (android.location.LocationListener) this);

	//	mLocationClient = new LocationClient(this, this, this);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		if (mapFragment != null) {
			googleMap = mapFragment.getMap();

			googleMap.setMyLocationEnabled(true);

			gps = new Geocoding(ChooseLocationOnMap.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();

				currentpoint = new LocationTuple(longitude, latitude);

			/*	myMarker = googleMap.addMarker(new MarkerOptions()
						.position(new LatLng(latitude, longitude))
						.title("My Spot")
						.snippet("This is my spot!")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.red_circle)));*/

				// \n is for new line

			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}



		} else {
			Toast.makeText(this, "Error - Map Fragment was null!!",
					Toast.LENGTH_SHORT).show();
		}
/*
		ImageButton searchButton = (ImageButton) findViewById(R.id.clom_searchButton);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final Dialog dialog = new Dialog(ChooseLocationOnMap.this);
				dialog.setContentView(R.layout.searchlocationdialog);
				dialog.setTitle("Input places to find");

				Button okButton = (Button) dialog
						.findViewById(R.id.searchLocationOkButton);
				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Geocoder geoCoder = new Geocoder(
								ChooseLocationOnMap.this);
						TextView txtaddress = (TextView) dialog
								.findViewById(R.id.searchLocationTextField);

						List<Address> addresses = null;
						try {
							addresses = geoCoder.getFromLocationName(txtaddress
									.getText().toString(), 5);
						}
						catch (IOException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									"Could not get address..!",
									Toast.LENGTH_LONG).show();
						}

						if (addresses == null || addresses.size() == 0) {
							Toast.makeText(getBaseContext(),
									"No Location found", Toast.LENGTH_SHORT)
									.show();
						}
						if (addresses.size() > 0) {
							double latitude = addresses.get(0).getLatitude();
							double longitude = addresses.get(0).getLongitude();

							// Clears all the existing markers on the map
							googleMap.clear();

							// Adding Markers on Google Map for each matching
							// address
							for (int i = 0; i < addresses.size(); i++) {

								Address address = (Address) addresses.get(i);

								// Creating an instance of GeoPoint, to display
								// in Google Map
								latLng = new LatLng(address.getLatitude(),
										address.getLongitude());
								point = new LocationTuple(longitude, latitude);

								addressText = String.format(
										"%s, %s",
										address.getMaxAddressLineIndex() > 0 ? address
												.getAddressLine(0) : "",
										address.getCountryName());

								markerOptions = new MarkerOptions();
								markerOptions.position(latLng);
								markerOptions.title(addressText);

								googleMap.addMarker(markerOptions);

								// Locate the first location
								if (i == 0)
									googleMap.animateCamera(CameraUpdateFactory
											.newLatLng(latLng));

							}
						} else {
							AlertDialog.Builder adb = new AlertDialog.Builder(
									ChooseLocationOnMap.this);
							adb.setTitle("Google Map");
							adb.setMessage("Please provide the proper place");
							adb.setPositiveButton("Close", null);
							adb.show();
						}

						dialog.dismiss();
					}

				});

				dialog.show();

			}
		});

		ImageButton okButton = (ImageButton) findViewById(R.id.clom_okButton);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.putExtra("POINT", point);
				//	    intent.putExtra("ADDRESS",addressText);
				if (getParent() == null) {
					setResult(Activity.RESULT_OK, intent);
				} else {
					getParent().setResult(Activity.RESULT_OK, intent);
				}
				Toast.makeText(
						getApplicationContext(),
						"location" + point.latitude + ":" + point.longitude
								+ "current loc" + currentpoint.latitude + ":"
								+ currentpoint.longitude, Toast.LENGTH_LONG)
						.show();

				double earthRadius = 3958.75;
				double dLat = Math.toRadians(point.latitude - currentpoint.latitude);
				double dLng = Math.toRadians(point.longitude - currentpoint.longitude);
				double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
						Math.cos(Math.toRadians(currentpoint.latitude)) * Math.cos(Math.toRadians(point.latitude)) *
								Math.sin(dLng / 2) * Math.sin(dLng / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				double dist = earthRadius * c;

				int meterConversion = 1609;
				float distance=(float) (dist * meterConversion);
				double distanceinkilometers = distance * 0.001;
				Toast.makeText(
						getApplicationContext(),
						"distance" + distanceinkilometers, Toast.LENGTH_LONG)
						.show();


				finish();
			}
		});


		ImageButton cancelButton = (ImageButton) findViewById(R.id.clom_cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});*/

	}

	 public static float distFrom(LocationTuple point, LocationTuple currentpoint) {
		    double earthRadius = 3958.75;
		    double dLat = Math.toRadians(point.latitude - currentpoint.latitude);
		    double dLng = Math.toRadians(point.longitude - currentpoint.longitude);
		    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
		               Math.cos(Math.toRadians(currentpoint.latitude)) * Math.cos(Math.toRadians(point.latitude)) *
		               Math.sin(dLng / 2) * Math.sin(dLng / 2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    double dist = earthRadius * c;

		    int meterConversion = 1609;
		    float distance=(float) (dist * meterConversion);
		    return distance;
		    }

	public void onMyLocationChange(Location arg0) {
		// TODO Auto-generated method stub

	}
	 public void onProviderDisabled(String arg0) {

	        Log.e("GPS", "provider disabled " + arg0);

	    }

	    public void onProviderEnabled(String arg0) {

	        Log.e("GPS", "provider enabled " + arg0);

	    }

	    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	        Log.e("GPS", "status changed to " + arg0 + " [" + arg1 + "]");

	    }

	/*
	 * Called when the Activity becomes visible.
	 *//*
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		if (isGooglePlayServicesAvailable()) {
			mLocationClient.connect();
		}

	}
*/
	/*
	 * Called when the Activity is no longer visible.
	 *//*
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}*/



	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 *//*
	public void onConnected(Bundle dataBundle) {

		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(1000); // Update location every second

		LocationServices.FusedLocationApi.requestLocationUpdates(
				mGoogleApiClient, mLocationRequest, this);
		// Display the connection status
		Location location = mLocationClient.getLastLocation();
		if (location != null) {
			Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT)
					.show();

			LatLng latLng = new LatLng(location.getLatitude(),
					location.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					latLng, 17);
			googleMap.animateCamera(cameraUpdate);
		} else {
			Toast.makeText(this,
					"Current location was null, enable GPS on emulator!",
					Toast.LENGTH_SHORT).show();
		}
	}*/

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */

	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */

	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry. Location services not available to you",
					Toast.LENGTH_LONG).show();
		}
	}

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mGoogleApiClient.disconnect();
		super.onStop();
	}

	@Override
	public void onConnected(Bundle bundle) {

		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(1000); // Update location every second

		LocationServices.FusedLocationApi.requestLocationUpdates(
				mGoogleApiClient, mLocationRequest, this);
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.i(TAG, "GoogleApiClient connection has been suspend");
	}

	@Override
	public void onLocationChanged(Location location) {
		mLocationView.setText("Location received: " + location.toString());
	}
}