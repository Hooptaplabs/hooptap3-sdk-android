package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Interfaces.TaskCallbackWithRetry;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public class TaskLauncher {
    private TaskWrapperInterface task;

    public TaskLauncher(TaskWrapperInterface task) {
        this.task = task;
    }

    public void executeTask(final TaskCallbackWithRetry callback) {
        task.executeTask(callback);
    }
}
