package com.hooptap.sdkbrandclub.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.hooptap.b.Interceptor;
import com.hooptap.b.Request;
import com.hooptap.b.Response;

import java.io.IOException;

public class HooptapInterceptor implements Interceptor {

    private final Context context;
    private int count = 0;

    public HooptapInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        SharedPreferences settings = context.getSharedPreferences("preferences", 0);
        Request request = chain.request();

        Response response = chain.proceed(request);
        if (!response.isSuccessful() && response.code() == 401) {
            if (count <1) {
                count++;

                String newToken = HooptapApi.getToken(settings.getString("ht_api_key", ""));

                Request newRequest = request.newBuilder()
                    .header("access_token", newToken)
                    .build();

                return chain.proceed(newRequest);
            }else{
                count = 0;
                return response;
            }

        }
        count = 0;
        return response;
    }

}