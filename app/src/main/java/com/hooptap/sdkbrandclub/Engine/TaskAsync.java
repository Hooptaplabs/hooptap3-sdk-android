package com.hooptap.sdkbrandclub.Engine;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.Interfaces.ErrorManagerInterface;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskConfiguratorInterface;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public class TaskAsync extends AsyncTask<Void, Void, Object> implements TaskWrapperInterface {

    private HooptapVClient client;
    private boolean errorHappends = false;
    private Exception exception;
    private HooptapCallback resultCallback;
    private Method method;
    private LinkedHashMap hasmap_data;
    private ErrorManagerInterface errorManager;

    @Override
    public TaskWrapperInterface createTask(TaskConfiguratorInterface taskConfigurator) {
        this.method = taskConfigurator.getMethod();
        this.hasmap_data = taskConfigurator.getHasmap_data();
        this.errorManager = taskConfigurator.getErrorManager();
        this.resultCallback = taskConfigurator.getResultCallback();
        client = Hooptap.getClient();
        return this;
    }

    @Override
    protected Object doInBackground(Void... params) {
        Object object = null;
        try {
            object = method.invoke(client, createArgumentsFromHasmap(hasmap_data));
        } catch (Exception e) {
            e.printStackTrace();
            errorHappends = true;
            exception = e;
        }
        return object;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (errorHappends) {
            errorManager.setException(exception);
        } else {
            if (result != null) {
                resultCallback.onSuccess(result);
            }
        }
    }

    //Tranforma un hasmap en un array(que es el formato que necesita el invoke del reflection)
    private Object[] createArgumentsFromHasmap(LinkedHashMap hasmap_data) {
        return hasmap_data.values().toArray();
    }

    @Override
    public void executeTask() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            this.execute();
        }
    }


}
