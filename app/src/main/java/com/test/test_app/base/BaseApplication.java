package com.test.test_app.base;

import android.app.Application;
import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;

public class BaseApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static class Send {
        public static class SendParams {
            //서버에서 인식하게 키, 서버로 보낼 값
            public LinkedHashMap<String, String> sendParams = new LinkedHashMap<>();

            //서버에서 인식하게 할 테이블 키, 서버로 보낼 테이블에 쿼리 (명시부터 조건까지 전부 기록한다.)
            public LinkedHashMap<String, String> sendDbTable = new LinkedHashMap<>();
        }

        public static JsonObject changeToJsonObj(SendParams sendParams) {
            LinkedHashMap<String, String> sendParamMap = sendParams.sendParams;

            JsonObject jsonMain = new JsonObject();
            JsonObject jsonDetail = new JsonObject();
            JsonArray jsonArr = new JsonArray();

            //jsonDeatil에 키와 값을 담는다.
            for(String key : sendParamMap.keySet()) {
                jsonDetail.addProperty(key, sendParamMap.get(key));
            }

            //jsonArr에 jsonDetail을 담는다.
            //데이터가 담긴 jsonArr을 다시 jsonObject에 담는다. 이때 키 값을 설정한다.
            jsonArr.add(jsonDetail);

            return null;
        }

        public static JsonObject changeToJsonTable(SendParams sendParams) {
            //아직 DB 까지는 미완성이므로 JSON OBJECT만 전송하는거.. (20221101)
            LinkedHashMap<String, String> sendDbTableMap = sendParams.sendDbTable;
        }
    }
}


