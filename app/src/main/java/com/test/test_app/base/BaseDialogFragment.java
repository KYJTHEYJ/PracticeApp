package com.test.test_app.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.test.test_app.database.DbUtil;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

// 베이스 다이얼로그 프레그먼트
public class BaseDialogFragment extends DialogFragment implements Observer {

    //region "Variables"
    public Activity actActivity = null; // 현재 액티비티 할당
    public Fragment fragContext = null; // 현재 프레그먼트 할당
    public DbUtil dbUtil = null; // 현재 DB객체 할당
    protected String tag = null; // 로그에 띄울 태그 변수 (이 프래그먼트를 상속한 프래그먼트는 로그 찍을 때 태그값으로 이 변수를 사용하면 됨)
    public HashMap<String, Object> selectResult = new HashMap<>(); // 다이얼로그에서 선택된 결과값을 저장

    /*
    protected UsInterfaceDialog.OnClickListener m_CancelClickListener = null;
    protected UsInterfaceDialog.OnClickListener m_OkClickListener = null;
    */

    //endregion "Variables"

    //region "LifeCycle"


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        BaseApplication.actActivity = getActivity(); // 현재 붙은 액티비티를 사용자 변수 객체에 할당
        actActivity = getActivity(); // 현재 붙은 액티비티 할당
        fragContext = this; // 사용하는 현재 프래그먼트 할당
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbUtil = BaseApplication.dbUtil; // 사용자 변수 클래스에 있는 DB유틸을 할당
        tag = getTag();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if (null != dialog) {
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                return false;
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*
    protected void setWindowSizeDialogFragment(HtListGrid htListGridView) {
        getDialog().setCancelable(false);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels - rect.top;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getDialog().getWindow().getAttributes());
        lp.width = screenWidth;
        lp.height = screenHeight;
        lp.horizontalMargin = 0;
        lp.verticalMargin = 0;
        Window window = getDialog().getWindow();
        window.setAttributes(lp);

        if (htListGridView != null) {
            htListGridView.setColmunSizeView(screenWidth, screenHeight, getDialog().getWindow().getDecorView());
            htListGridView.columnWidhtReSize();
        }
    }
    */
    //endregion "LifeCycle"


    @Override
    public void update(Observable observable, Object readingData) {
        /*
        if (observable instanceof ObservableObjectBarcode) { // 바코드 (Object readingData => 리딩된 값)
            processReadingBarcode(readingData.toString());
        }
        */
    }

    /*
    protected void processReadingBarcode(String boxBarcode) {

    }
    */

}