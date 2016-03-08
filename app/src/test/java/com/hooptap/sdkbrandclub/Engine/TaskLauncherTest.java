package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Interfaces.TaskCallbackWithRetry;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;
import com.hooptap.sdkbrandclub.Models.ResponseError;

import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by carloscarrasco on 8/3/16.
 */
public class TaskLauncherTest {

    @Test
    public void verifyIfLauncherCallToExecuteMethodInMyInstanceTask(){

        TaskCallbackWithRetry callback = new TaskCallbackWithRetry() {

            @Override
            public void retry() {

            }

            @Override
            public void onSuccess(Object var) {

            }

            @Override
            public void onError(ResponseError var) {

            }
        };

        TaskWrapperInterface taskfake = Mockito.mock(TaskWrapperInterface.class);
        new TaskLauncher(taskfake).executeTask(callback);


        verify(taskfake, atLeastOnce()).executeTask(isA(TaskCallbackWithRetry.class));
    }


}