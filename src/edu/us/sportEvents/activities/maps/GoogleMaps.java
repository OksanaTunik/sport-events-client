package edu.us.sportEvents.activities.maps;

/**
 * Created by Oksana on 17.05.2015.
 */

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import edu.us.sportEvents.activities.maps.LocationTuple;

public class GoogleMaps implements LocationListener {
    private double latitude;
    private double longitude;

    public GoogleMaps(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    public LocationTuple getLocation() {
        return new LocationTuple(longitude, latitude);
    }
}
