package com.ekaruztech.smartkolo.object.responses;

import com.ekaruztech.smartkolo.object._Meta;
import com.ekaruztech.smartkolo.util.Logger;
import com.ekaruztech.smartkolo.util.MyUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Femi on 7/29/2016.
 */
public class GeneralResponse  {

    public static final String TAG = GeneralResponse.class
            .getSimpleName();

    JSONObject response=null;
    ResponseBody responseBody;
    public GeneralResponse(Response<ResponseBody> rb) {
        this.responseBody= (rb.body() != null) ? rb.body() : rb.errorBody();
        try{
           this.response=new JSONObject(responseBody.string());
        }catch (Exception je) {
            Logger.write(je);
        }
    }

    public  <T> List<T> getDataAsList(String key, Class<T> classOfT)  throws Exception {
        if(response==null) response=new JSONObject(responseBody.string());
        JSONArray ja=response.getJSONArray(key);
        Logger.write(ja.toString());
        Gson gson=new Gson();
        int len=ja.length();
        List<T> list = new ArrayList<T>(len);
        // List<Object> list=(List<Object>)l;
        for(int i=0; i<len; i++) {
            list.add(gson.fromJson(ja.getString(i), classOfT));
        }
        return list;
    }
    public <T> T getData(String key, Class<T> classOfT) throws Exception {
        if(response==null) response=new JSONObject(responseBody.string());
        Gson gson=new Gson();
        return  gson.fromJson(response.getString(key), (Type) classOfT);

    }
    public _Meta getMeta() throws Exception {
        if(response==null) response=new JSONObject(responseBody.string());
        return MyUtils.getMeta(response);
    }

    public APIError getError() throws Exception {
        if(response==null) response=new JSONObject(responseBody.string());
        return MyUtils.getError(response);
    }

    public boolean isAuthFailed() throws Exception {
        if(response==null) response=new JSONObject(responseBody.string());
        return MyUtils.isAuthFailed(response);
    }
    public String toString() {
        return response.toString();
    }
}
