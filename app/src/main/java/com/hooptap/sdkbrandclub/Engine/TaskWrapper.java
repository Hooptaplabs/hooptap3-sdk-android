package com.hooptap.sdkbrandclub.Engine;

import android.os.StrictMode;
import android.util.Log;

import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.brandclub.model.InputRenewTokenModel;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskRetryCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Utils;

/**
 * Created by carloscarrasco on 2/3/16.
 */
public class TaskWrapper {
    private final TaskConfiguration taskConfigurator;
    private TaskWrapperInterface task;

    public TaskWrapper(TaskConfiguration taskConfigurator) {
        this.taskConfigurator = taskConfigurator;
        createTask();
    }

    private void createTask() {
        ErrorManager errorManager = new ErrorManager(this);
        taskConfigurator.setErrorManager(errorManager);
        task = new TaskAsync().createTask(taskConfigurator);
    }

    public TaskWrapperInterface getTask() {
        return task;
    }

    public void retryTask(TaskRetryCallback retryCallback){
        createTask();
        retryCallback.retryTask(task);
    }

    public boolean renewToken(HooptapCallback resultCallback) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HooptapVClient client = Hooptap.getClient();

        InputRenewTokenModel renewTokenModel = new InputRenewTokenModel();
        String oldToken = Hooptap.getToken().substring(("Bearer ").length());
        renewTokenModel.setToken(oldToken);
        try {
            Object newToken = client.userIdTokenPut(Hooptap.getApiKey(), oldToken, Hooptap.getUserId(), renewTokenModel);
            Utils.setToken(newToken);
            changeValueOfTokenOnHasmapData(Hooptap.getToken());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("RENEWTOKEN-ERROR", e.getLocalizedMessage() + " /");
            ResponseError responseError = new ResponseError();
            responseError.setReason(e.getLocalizedMessage());
            resultCallback.onError(responseError);
            return false;
        }
    }

    private void changeValueOfTokenOnHasmapData(String newToken) {
        taskConfigurator.getHasmap_data().put("token", newToken);
    }

}
