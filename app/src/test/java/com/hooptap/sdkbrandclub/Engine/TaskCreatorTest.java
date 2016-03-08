package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by carloscarrasco on 7/3/16.
 */
public class TaskCreatorTest {
    private static final String BADGE_DETAIL_METHODNAME = "rewardIdGet";

    public TaskWrapperInterface createFakeTaskWrapper() {
        TaskConfiguration taskConfigurator = new TaskConfiguration();
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", "HT_Api");
        data.put("token", "HT_Token");
        data.put("id", "Badge_Id");
        try {
            Class[] args = {String.class, String.class, String.class};
            Class cls = HooptapVClient.class;
            Method method = cls.getMethod(BADGE_DETAIL_METHODNAME, args);
            taskConfigurator.setMethod(method);
            taskConfigurator.setHasmap_data(data);
            taskConfigurator.setErrorManager(new ErrorManager());
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }

        return new TaskWrapper(taskConfigurator).getTask();
    }

    @Test
    public void testCreateTaskWithStringMethodAndHasmapDataForBadgeDetail() {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", "HT_Api");
        data.put("token", "HT_Token");
        data.put("id", "Badge_Id");

        TaskCreator taskCreator = new TaskCreator(BADGE_DETAIL_METHODNAME, data);
        TaskWrapperInterface wrapperTask = taskCreator.getWrapperTask();

        TaskWrapperInterface fakeTask = createFakeTaskWrapper();

        assertReflectionEquals(fakeTask, wrapperTask);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateTaskWithNonExistStringMethodAndThrowException() {
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", "HT_Api");
        data.put("token", "HT_Token");
        data.put("id", "Badge_Id");

        new TaskCreator("NonExistMethod", data);

    }
}