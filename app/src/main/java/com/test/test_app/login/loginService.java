package com.test.test_app.login;

import com.google.gson.JsonObject;
import com.test.test_app.network.BaseResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface loginService {
    @POST("android/login.do")
    Single<Response<BaseResponse<loginDto>>> login(@Body JsonObject requestParameterJsonObj);
}
