package com.hooptap.sdkbrandclub.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hooptap.sdkbrandclub.Api.HooptapApiAWS;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;

import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * Created by root on 22/12/15.
 */
public class Utils {
    private HooptapApiAWS dbc;

    /* This method returns true if the collection is null or is empty.
    * @param collection
    * @return true | false
            */
    public static boolean isEmpty( Collection<?> collection ){
        if( collection == null || collection.isEmpty() ){
            return true;
        }
        for(Object c : collection)
        {
            if (isEmpty(c))
                return true;
        }
        return false;
    }

    /**
     * This method returns true of the map is null or is empty.
     * @param map
     * @return true | false
     */
    public static boolean isEmpty( Map<?, ?> map ){
        if( map == null || map.isEmpty() ){
            return true;
        }
        Collection<?> values = map.values(); // returns a Collection with all the objects
        for(Object c : values)
        {
            if (isEmpty(c))
                return true;
        }
        return false;
    }

    /**
     * This method returns true if the objet is null.
     * @param object
     * @return true | false
     */
    public static boolean isEmpty( Object object ){
        if( object == null ){
            return true;
        }
        if(object instanceof String){
            String value= (String) object;
            return isEmpty(value);
        }
        return false;
    }

    /**
     * This method returns true if the input array is null or its length is zero.
     * @param array
     * @return true | false
     */
    public static boolean isEmpty( Object[] array ){
        if( array == null || array.length == 0 ){
            return true;
        }
        for(Object a:array){
            if(isEmpty(a)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns true if the input string is null or its length is zero.
     * @param string
     * @return true | false
     */
    public static boolean isEmpty( String string ){
        if( string == null || string.trim().length() == 0 ){
            return true;
        }
        return false;
    }

    public static void dialogInfo(Context context){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

// set title
        alertDialogBuilder.setTitle("Your Title");

// set dialog message
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

// show it
        alertDialog.show();
    }

}
