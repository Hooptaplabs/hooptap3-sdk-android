package com.hooptap.sdkbrandclub.Utilities;

/**
 * Created by root on 12/01/16.
 */
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/7/15.
 */
public class LocationManager implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private WebView webview;

    public void getLocation(Context activity, WebView webview){
        this.webview = webview;

        createLocationRequest();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("onLocationChanged",location.getLatitude()+" / "+location.getLongitude());
        JSONObject json = new JSONObject();
        try {
            json.put("latitude", location.getLatitude());
            json.put("longitude", location.getLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            callJavascriptFuction("HT.setLocation('" + json + "');");
        } else {
            callJavascriptFuction("javascript:HT.setLocation('" + json + "');");
        }
    }

    public void callJavascriptFuction(final String fuction){
        webview.post(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webview.evaluateJavascript(fuction, null);
                } else {
                    webview.loadUrl(fuction);
                }
            }
        });
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
}