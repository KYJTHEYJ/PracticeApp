package com.test.test_app.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//앱 메모리 및 쓰레드 처리에 유연하기 위해 Singleton (LazyHolder 패턴) 으로 작성
public class HttpClient {
    //접속 정보
    //Local
    private static final String BASE_URL = "http://211.45.66.209:8180/sgnx/";

    //싱글톤 생성자 (직접 생성 방지)
    private HttpClient() { }


    //인스턴스 반환
    public static Retrofit getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
