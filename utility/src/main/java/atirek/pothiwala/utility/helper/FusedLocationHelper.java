package atirek.pothiwala.utility.helper;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.NonNull;

import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class FusedLocationHelper extends Service {

    public interface LocationListener {
        void onLocationReceived(@NonNull Location location);

        void onLocationAvailability(boolean isAvailable);
    }

    private final Context context;
    private final String TAG;
    private final boolean enableDebug;

    private void checkLog(Object data) {
        if (enableDebug) {
            Log.d(TAG + ">>", data.toString());
        }
    }

    // The minimum time between updates in milliseconds
    private long TIME_INTERVAL = 5000;

    // The minimum distance between updates in metres
    private long DISPLACEMENT = 10;

    // The priority for location updates
    private int PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;

    private LocationRequest locationRequest;
    private FusedLocationProviderClient locationProviderClient;
    private LocationListener listener;

    public FusedLocationHelper(Context context, String TAG, boolean enableDebug) {
        this.context = context;
        this.TAG = TAG;
        this.enableDebug = enableDebug;
    }

    public void setListener(LocationListener listener) {
        this.listener = listener;
    }

    public void setPriority(int priority) {
        this.PRIORITY = priority;
    }

    public void setTimeInterval(long milliseconds) {
        this.TIME_INTERVAL = milliseconds;
    }

    public void setDisplacement(long metres) {
        this.DISPLACEMENT = metres;
    }

    public void initializeLocationProviders() {
        checkLog("Initialized");
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(PRIORITY);
        locationRequest.setInterval(TIME_INTERVAL);
        locationRequest.setFastestInterval(TIME_INTERVAL);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        checkLog("Started");
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            Location location = locationResult.getLastLocation();
            if (listener != null) {
                checkLog("Position: " + location.toString());
                listener.onLocationReceived(location);
            }
        }

        @Override
        public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);

            boolean isAvailable = locationAvailability.isLocationAvailable();
            if (listener != null) {
                listener.onLocationAvailability(isAvailable);
            }

            String message = "Location Available";
            if (!isAvailable) {
                message = "Location Not Available";
            }
            checkLog(message);
        }
    };

    public void stopLocationUpdates() {
        checkLog("Stopped");
        if (locationProviderClient != null && locationCallback != null) {
            locationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public static Address getGeoAddress(Context context, Location position, String language) {
        try {
            Geocoder geocoder = new Geocoder(context, new Locale(language));
            List<Address> addresses = geocoder.getFromLocation(position.getLatitude(), position.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
