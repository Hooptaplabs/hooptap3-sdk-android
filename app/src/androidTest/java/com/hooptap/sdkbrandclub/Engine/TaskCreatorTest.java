package com.hooptap.sdkbrandclub.Engine;

import android.test.InstrumentationTestCase;

import com.hooptap.sdkbrandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class TaskCreatorTest extends InstrumentationTestCase {

    private static final String BADGE_DETAIL_METHODNAME = "rewardIdGet1";
    private TaskWrapperInterface taskFake;

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


}