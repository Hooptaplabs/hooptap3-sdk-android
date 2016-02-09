package com.hooptap.sdkbrandclub.Models;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class ListParameterizedType implements ParameterizedType {

    private Type type;

    private ListParameterizedType(Type type) {
        this.type = type;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {type};
    }

    @Override
    public Type getRawType() {
        return ArrayList.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    // implement equals method too! (as per javadoc)
}