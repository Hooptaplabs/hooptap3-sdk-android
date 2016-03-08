package com.hooptap.sdkbrandclub.Interfaces;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public interface TaskWrapperInterface {

    TaskWrapperInterface createTask(TaskConfiguratorInterface taskConfigurator);

    void executeTask(TaskCallbackWithRetry callback);


}
