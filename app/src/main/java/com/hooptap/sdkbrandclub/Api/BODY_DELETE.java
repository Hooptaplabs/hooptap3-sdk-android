package com.hooptap.sdkbrandclub.Api;

/**
 * Created by Carliso on 25/02/2015.
 */
import com.hooptap.a.http.RestMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target(METHOD)
@Retention(RUNTIME)
@RestMethod(value = "DELETE", hasBody = true)
public @interface BODY_DELETE {
    String value();
}
