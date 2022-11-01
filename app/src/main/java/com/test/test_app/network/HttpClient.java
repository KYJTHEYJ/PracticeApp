package com.test.test_app.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//앱 메모리 및 쓰레드 처리에 유연하기 위해 Singleton (LazyHolder 패턴) 으로 작성
public class HttpClient {
    //접속 정보
    private static final String BASE_URL = "http://localhost:8180";

    //인스턴스
    private static HttpClient instance = null;

    //싱글톤 생성자
    private HttpClient() { }

    public Retrofit getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //Retrofit + RxJava 하기 위해서는 필수
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
