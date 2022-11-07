package com.test.test_app.login;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface loginService {
    @POST("app/appLogin.do")
    Call<String> login(@Body JsonObject requestParameterJsonObj);

    @POST("app/appInsert.do")
    Call<String> InsertChk(@Body JsonObject requestParameterJsonObj);
}
