package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;

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
        task = new TaskAsync().createTask(taskConfigurator);
    }

    public TaskWrapperInterface getTask() {
        return task;
    }

}
