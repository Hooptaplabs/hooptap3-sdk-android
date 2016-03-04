package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskRetryCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public class TaskLauncher {
    private TaskWrapperInterface task;

    public TaskLauncher(TaskWrapperInterface task) {
        this.task = task;
    }

    public void executeTask(final HooptapCallback callback) {
        task.executeTask(callback, new TaskRetryCallback() {
            @Override
            public void retryTask(TaskWrapperInterface retryTask) {
                task = retryTask;
                executeTask(callback);
            }
        });
    }
}
