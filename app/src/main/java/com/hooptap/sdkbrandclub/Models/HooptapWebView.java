package com.hooptap.sdkbrandclub.Models;

/**
 * Created by root on 12/01/16.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hooptap.sdkbrandclub.Interfaces.HooptapJSInterface;
import com.hooptap.sdkbrandclub.Utilities.LocationManager;

import org.json.JSONObject;

/**
 * WebView Custom que utilizaremos para poder recibir un feedback de los juegos HTML5
 * <p/>
 * Incorpora un metodo en el cual se le añade una interfaz (@see HooptapJSInterface) para facilitar la
 * interaccion entre el juego y el cliente
 *
 * @author Hooptap Team
 * @see HooptapJSInterface
 */
public class HooptapWebView extends WebView {
    private Context context;
    private HooptapJSInterface inter;
    public LocationManager locationManager;

    public HooptapWebView(Context context) {
        super(context);
        this.context = context;
        addJAvaScriptInterface();
    }

    public HooptapWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        addJAvaScriptInterface();
    }

    public HooptapWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        addJAvaScriptInterface();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HooptapWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        addJAvaScriptInterface();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public HooptapWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        this.context = context;
        addJAvaScriptInterface();
    }

    private void addJAvaScriptInterface() {
        this.onAttachedToWindow();
        this.getSettings().setJavaScriptEnabled(true);
        this.addJavascriptInterface(new HTJavaScriptInterface() {


            @JavascriptInterface
            @Override
            public void gameDidFinishAndroid(String json) {
                if (inter != null) {
                    try {
                        JSONObject jsonGame = new JSONObject(json);
                        HooptapGameStatus gameStatus = new HooptapGameStatus();
                        gameStatus.setScore(jsonGame.getString("points"));
                        inter.gameDidFinish(gameStatus);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @JavascriptInterface
            @Override
            public void gameDidStart() {
                if (inter != null) {
                    inter.gameDidStart();
                }
            }

            @JavascriptInterface
            @Override
            public void getLocation() {
                locationManager = new LocationManager();
                locationManager.getLocation(context, HooptapWebView.this);
            }

        }, "HT");
    }

    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        if (locationManager != null){
            if (locationManager.mGoogleApiClient.isConnected()) {
                locationManager.stopLocationUpdates();
                locationManager.mGoogleApiClient.disconnect();
            }
        }

    }

    /**
     * Metodo necesario para poder recibir las interacciones de JavaScript del juego
     *
     * @param inter Interfaz de la clase HooptaJSInterfaz necesaria para poder recibir las interacciones del juego
     * @see HooptapJSInterface
     */
    public void addInterface(HooptapJSInterface inter) {
        this.inter = inter;
    }

    private interface HTJavaScriptInterface {

        @JavascriptInterface
        void gameDidFinishAndroid(String json);

        @JavascriptInterface
        void gameDidStart();

        @JavascriptInterface
        void getLocation();

    }

}