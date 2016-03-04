package com.hooptap.sdkbrandclub.Engine;

import android.test.InstrumentationTestCase;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class CommandTest extends InstrumentationTestCase {

    private static final String BADGE_LIST_METHODNAME = "userIdBadgesGet";

    private static <T> T invokeStaticMethod(Class targetClass, String methodName, Class[] argClasses,
                                            Object[] argObjects) throws InvocationTargetException {
        try {
            Method method = targetClass.getDeclaredMethod(methodName, argClasses);
            method.setAccessible(true);

            Constructor constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (T) method.invoke(constructor.newInstance(), argObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testGetParametersOfReflectionMethod() {

        Class[] argClasses = {String.class};
        Object[] argObjects = {BADGE_LIST_METHODNAME};

        try {
            Class[] classArray = invokeStaticMethod(TaskCreator.class, "getParametersOfReflectionMethod", argClasses, argObjects);
            assertThat(classArray[0].getCanonicalName(), is("java.lang.String"));
            assertThat(classArray.length, is(3));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMethodByReflection() {
        Class[] argClasses = {String.class, Class[].class};
        Class[] clas = new Class[]{String.class, String.class, String.class};
        Object[] argObjects = {BADGE_LIST_METHODNAME, clas};

        try {
            Method method = invokeStaticMethod(TaskCreator.class, "getMethodByReflection", argClasses, argObjects);
            assertThat(method.getName(), is("userUserIdBadgesGet"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}