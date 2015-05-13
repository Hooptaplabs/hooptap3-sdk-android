package com.hooptap.sdkgameroom.Customs;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hooptap.sdkgameroom.Interfaces.HooptapJSInterface;

import org.json.JSONObject;

/**
 * WebView Custom que utilizaremos para poder recibir un feedback de los juegos HTML5
 *
 * Incorpora un metodo en el cual se le añade una interfaz (@see HooptapJSInterface) para facilitar la
 * interaccion entre el juego y el cliente
 *
 * @see HooptapJSInterface
 *
 * @author Hooptap Team
 */
public class HooptapWebView extends WebView {
    private HooptapJSInterface inter;

    public HooptapWebView(Context context) {
        super(context);
        addJAvaScriptInterface();
    }

    public HooptapWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addJAvaScriptInterface();
    }

    public HooptapWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addJAvaScriptInterface();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HooptapWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        addJAvaScriptInterface();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public HooptapWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        addJAvaScriptInterface();
    }

    private void addJAvaScriptInterface(){
        this.getSettings().setJavaScriptEnabled(true);
        this.addJavascriptInterface(new HTJavaScriptInterface() {
            @JavascriptInterface
            @Override
            public void gameDidFinishAndroid(String json) {
                if (inter != null){
                    try{
                        JSONObject jsonGame = new JSONObject(json);
                        HooptapGameStatus gameStatus = new HooptapGameStatus();
                        gameStatus.setPoints(jsonGame.getInt("points"));
                        inter.gameDidFinish(gameStatus);
                    }catch (Exception e){e.printStackTrace();}

                }
            }

            @JavascriptInterface
            @Override
            public void gameDidStart() {
                if (inter != null) {
                    inter.gameDidStart();
                }
            }
        }, "HT");
    }

    /**
     * Metodo necesario para poder recibir las interacciones de JavaScript del juego
     * @param inter Interfaz de la clase HooptaJSInterfaz necesaria para poder recibir las interacciones del juego
     *
     * @see HooptapJSInterface
     */
    public void addInterface(HooptapJSInterface inter){
        this.inter = inter;
    }

    private interface HTJavaScriptInterface {

        @JavascriptInterface
        void gameDidFinishAndroid(String json);

        @JavascriptInterface
        void gameDidStart();

        //@JavascriptInterface
        //String giveResolution();


    }

}
