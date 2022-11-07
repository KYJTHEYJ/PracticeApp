package com.test.test_app.base;

import android.app.Activity;
import android.os.Bundle;

import com.test.test_app.database.DbUtil;

// 베이스 액티비티
public class BaseActivity extends Activity {

    //region "Variables"
    public Activity m_ActActivity; // 현재 액티비티 변수
    //endregion "Variables"

    //region "LifeCycle"
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_ActActivity = this; // 현재 액티비티 할당

        if (BaseApplication.dbUtil == null) {
            BaseApplication.dbUtil = new DbUtil(getApplicationContext()); // 사용자 변수 객체에 DB변수 할당
        }

        /*
        if (BaseApplication.m_Vibrator == null) {
            BaseApplication.m_Vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE); // 사용자 변수 객체에 진동 객체 할당
        }
        */
    }

    @Override
    protected void onResume() {
        super.onResume();

        BaseApplication.actActivity = this; // 액티비티가 재개되면 사용자 변수 객체에 할당
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    //endregion "LifeCycle"
}