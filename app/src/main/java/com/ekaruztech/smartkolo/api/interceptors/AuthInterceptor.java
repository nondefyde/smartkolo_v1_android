package com.ekaruztech.smartkolo.api.interceptors;

import android.content.Context;

import com.ekaruztech.smartkolo.object._Meta;
import com.ekaruztech.smartkolo.util.Logger;
import com.ekaruztech.smartkolo.util.MyUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Emmanuel on 11/21/2016.
 */

public class AuthInterceptor implements Interceptor {

    protected Context context;
    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        JSONObject json=null;
        try{
            json =new JSONObject(response.body().string());
            _Meta meta = MyUtils.getMeta(json);
            if(meta.getStatus_code() == 401){
                Logger.write(String.format("json body, %s", json.toString()));
                return null;
            }
        }catch (Exception je) {
            Logger.write(je);
        }
        return response;
    }
}
