package com.ekaruztech.smartkolo.api.services;


import com.ekaruztech.smartkolo.api.Routes;
import com.ekaruztech.smartkolo.object.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Emmanuel on 5/19/2016.
 */
public interface AuthApi {

    @FormUrlEncoded
    @POST(Routes.AUTH_LOGIN_ENDPOINT)
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password , @Field("device_token") String device_token);


    @POST(Routes.AUTH_REGISTER_ENDPOINT)
    Call<ResponseBody> register(@Body User user);

    @FormUrlEncoded
    @POST(Routes.AUTH_VERIFY_ENDPOINT)
    Call<ResponseBody> verify(@Field("verification_code") String verification_code);
}
