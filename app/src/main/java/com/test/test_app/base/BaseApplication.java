package com.test.test_app.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.test.test_app.database.DbUtil;
import com.test.test_app.network.HttpClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import okhttp3.ResponseBody;

public class BaseApplication extends Application {
    //region "Variable"
    public static Context context;
    public static Activity actActivity = null;
    public static Fragment fragContext = null;
    public static Application application = null;

    //SQLITE 기반 DB 제어 관련 변수
    public static DbUtil dbUtil = null;
    //endregion

    //region "Class"
    //사용자정보 클래스
    public static class UserInfo {
        public static String COMP_CD = "";
        public static String COMP_NM = "";
        public static String USER_NM = "";
        public static String USER_ID = "";
        public static String ADMIN_YN = "";
        public static String EMPL_LEVEL = "";
        public static String WMS_AUTH = "";
        public static String USER_SUPPLIER_YN = "";

        //public static ArrayList<loginDto.loginMenuDto> USER_MAINMENU = new ArrayList<>(); // 메뉴 대분류
        //public static ArrayList<loginDto.loginMenuDto> USER_SUBMENU = new ArrayList<>(); // 메뉴 소분류 (대분류 하위)
        //public static ArrayList<loginDto.loginAuthDto> USER_AUTH = new ArrayList<>(); // 사용자 권한

        public static String USER_SELECTED_WAREHOUSE_CD = "";
        public static String USER_SELECTED_WAREHOUSE_LOC_USE_YN = "";
    }

    //화면 컬럼 사이즈 클래스
    public class columnsSize {
        public static final int DEFAULT_COL = 130;
        public static final int DEFAULT_ROW = 50;

        public static final int SIZE_CD = 70;
        public static final int QTY = 70;

        public static final int SHOP_CD = 95;
        public static final int SHOP_NM = 130;

        public static final int CHECK = 50;
        public static final int SEND_YN = 95;

        public static final int SEND_DE = 230;

        public static final int WORK_DE = 120;
    }

    //토스트 메세지 클래스
    public static class toast { // 공통 토스트
        //region "토스트 (메세지 표시 시간 LONG)"
        public static void showToast(Context context, boolean show, CharSequence text) {
            if (show) {
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        public static void showToast(Context context, CharSequence text) { // 토스트1 : 베이스 메서드 (텍스트를 넘김)
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        }

        public static void showToast(CharSequence text) { // 토스트1 : 유저밸류에 저장된 액티비티에 토스트를 띄움
            showToast(BaseApplication.actActivity, text);
        }

        public static void showToast(Context context, int res) { // 토스트2 : 베이스 메서드 (스트링 밸류 값을 넘김)
            Toast.makeText(context, res, Toast.LENGTH_LONG).show();
        }

        public static void showToast(int res) { // 토스트2 : 유저밸류에 저장된 액티비티에 토스트 띄움 (스트링 밸류 값을 넘김)
            showToast(BaseApplication.actActivity, res);
        }
        //endregion "토스트 (메세지 표시 시간 LONG)"

        //region "토스트 (메세지 표시 시간 SHORT)"
        public static void showShortToast(Context context, CharSequence text) {
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }

        public static void showShortToast(CharSequence text) {
            showShortToast(BaseApplication.actActivity, text);
        }

        public static void showShortToast(Context context, int res) {
            Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
        }

        public static void showShortToast(int res) {
            showShortToast(BaseApplication.actActivity, res);
        }
        //endregion "토스트 (메세지 표시 시간 SHORT)"
    }

    //OLD ProgressDialog
    /*
    public static class progressDialog {
        public static void dismiss(ProgressDialog progressDialog) {
            if (progressDialog != null || progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        //STYLE_SPINNER
        public static ProgressDialog show(final Activity actActivity, int strTitle, int strContent, boolean bCancel) {
            return show(actActivity, actActivity.getString(strTitle), actActivity.getString(strContent), "", bCancel, null);
        }

        public static ProgressDialog show(final Activity actActivity, String strTitle, String strContent, boolean bCancel) {
            return show(actActivity, strTitle, strContent, "", bCancel, null);
        }

        public static ProgressDialog show(final Activity actActivity, int strTitle, int strContent, int strCancel, boolean bCancel, DialogInterface.OnClickListener OncancelClick) {
            return show(actActivity, actActivity.getString(strTitle), actActivity.getString(strContent), actActivity.getString(strCancel), bCancel, OncancelClick);
        }

        public static ProgressDialog show(final Activity actActivity, String strTitle, String strContent, String strCancel, boolean bCancel, DialogInterface.OnClickListener OncancelClick) {
            ProgressDialog pd = new ProgressDialog(actActivity);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(bCancel);

            if (!TextUtils.isEmpty(strTitle)) {
                pd.setTitle(strTitle);
            }

            pd.setMessage(strContent);

            if (OncancelClick != null) {
                pd.setButton(DialogInterface.BUTTON_NEGATIVE, strCancel, OncancelClick);
            }
            pd.show();
            return pd;
        }

        //STYLE_HORIZONTAL
        public static ProgressDialog show(final Activity actActivity, int strTitle, int strContent, boolean bCancel, int iMaxProgress) {
            return show(actActivity, actActivity.getString(strTitle), actActivity.getString(strContent), "", bCancel, iMaxProgress, null);
        }

        public static ProgressDialog show(final Activity actActivity, String strTitle, String strContent, boolean bCancel, int iMaxProgress) {
            return show(actActivity, strTitle, strContent, "", bCancel, iMaxProgress, null);
        }

        public static ProgressDialog show(final Activity actActivity, int strTitle, int strContent, int strCancel, int iMaxProgress, boolean bCancel, DialogInterface.OnClickListener OncancelClick) {
            return show(actActivity, actActivity.getString(strTitle), actActivity.getString(strContent), actActivity.getString(strCancel), bCancel, iMaxProgress, OncancelClick);
        }

        public static ProgressDialog show(final Activity actActivity, String strTitle, String strContent, String strCancel, boolean bCancel, int iMaxProgress, DialogInterface.OnClickListener OncancelClick) {
            ProgressDialog pd = new ProgressDialog(actActivity);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCancelable(bCancel);
            pd.setMax(iMaxProgress);

            if (!TextUtils.isEmpty(strTitle)) {
                pd.setTitle(strTitle);
            }

            pd.setMessage(strContent);

            if (OncancelClick != null) {
                pd.setButton(DialogInterface.BUTTON_NEGATIVE, strCancel, OncancelClick);
            }

            pd.show();

            return pd;
        }
    }
    */

    public static class setProgressDialog {
        static AlertDialog dialog;

        public static void setProgressDialog(String txtLoading) {
            LinearLayout linearLayout = new LinearLayout(actActivity);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            linearLayout.setPadding(30, 30, 30, 30);

            linearLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams linearLayoutParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayoutParam.gravity = Gravity.CENTER;
            linearLayout.setLayoutParams(linearLayoutParam);

            ProgressBar progressBar = new ProgressBar(actActivity);
            progressBar.setIndeterminate(true);
            progressBar.setPadding(0, 0, 30, 0);
            progressBar.setLayoutParams(linearLayoutParam);

            linearLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            linearLayoutParam.gravity = Gravity.CENTER;

            TextView tvText = new TextView(actActivity);

            if (txtLoading.isEmpty()) {
                tvText.setText("처리 중");
            } else {
                tvText.setText(txtLoading);
            }

            tvText.setTextColor(Color.parseColor("#000000"));
            tvText.setTextSize(20);
            tvText.setLayoutParams(linearLayoutParam);

            linearLayout.addView(progressBar);
            linearLayout.addView(tvText);

            AlertDialog.Builder builder = new AlertDialog.Builder(actActivity);

            // 다이얼로그 처리 중에는 꺼지지 않도록..
            builder.setCancelable(false);

            builder.setView(linearLayout);

            dialog = builder.create();
            dialog.show();

            Window window = dialog.getWindow();

            if (window != null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);
            }
        }

        public static void setProgressDialogDissmiss() {
            if (dialog != null || dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    //endregion

    //region "LifeCycle"
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }
    //endregion

    //region "Functions"
    //region "Communication Server"
    public static class Send {
        public static class SendParams {
            //서버에서 인식하게 키, 서버로 보낼 값
            public LinkedHashMap<String, String> sendParams = new LinkedHashMap<>();

            //서버에서 인식하게 할 테이블 키, 서버로 보낼 테이블에 쿼리 (조건문부터 기록한다.)
            public LinkedHashMap<String, String> sendDbTable = new LinkedHashMap<>();
        }

        public static JsonObject changeToJsonObj(SendParams sendParams) {
            LinkedHashMap<String, String> sendParamMap = sendParams.sendParams;

            //jsonDetail -> 실질적 데이터, jsonArr -> 데이터들의 모임, jsonMain -> 서버로 전송되는 json 모임
            JsonObject jsonMain = new JsonObject();
            JsonObject jsonDetail = new JsonObject();
            JsonArray jsonArr = new JsonArray();

            for(String key : sendParamMap.keySet()) {
                jsonDetail.addProperty(key, sendParamMap.get(key));
            }

            jsonArr.add(jsonDetail);
            jsonMain.add("DATA", jsonArr);

            return jsonMain;
        }

        //복수개 테이블을 담았을 때도 처리가 가능 하도록 조치한다.
        public static JsonObject changeToJsonTable(SendParams sendParams) {
            LinkedHashMap<String, String> sendDbTableMap = sendParams.sendDbTable;
            DbUtil dbUtil = BaseApplication.dbUtil;

            //jsonDetail -> 실질적 데이터, jsonArr -> 데이터들의 모임, jsonMain -> 서버로 전송되는 json 모임
            JsonObject jsonMain = new JsonObject();
            JsonObject jsonDetail;
            JsonArray jsonArr;

            Iterator<String> iterator = sendDbTableMap.keySet().iterator();

            //복수개 테이블을 이름을 선언하여 담는데, 이를 구분자 "/" 로 담고, 추후 나누어서 로컬 DB내 테이블의 데이터들을 로드하는데 사용한다.
            String tableName = "";
            while(iterator.hasNext()) {
                tableName += iterator.next() + "/";
            }

            String[] tableNameArr = tableName.split("/");

            //처리할 SQL문
            String SQL = "";

            for(String tableNm : tableNameArr) {
                jsonArr = new JsonArray();

                SQL = "SELECT * FROM " + tableNm + sendDbTableMap.get(tableNm);

                //위에 선언된 SQL에 맞춰 DB 정보를 가져와둔다.
                dbUtil.dbParams.clear();
                dbUtil.queryOpen(SQL);

                //DB정보의 컬럼 이름을 받아 저장한다.
                String[] columnNameArr = dbUtil.dbCursor.getColumnNames();

                //가져온 DB 정보를 컬럼이름 별로 해체하여 데이터를 가져와 기록한다.
                for(int row = 0; row < dbUtil.recordCount(); row++) {
                    jsonDetail = new JsonObject();

                    for(String columnNm : columnNameArr) {
                        jsonDetail.addProperty(columnNm, dbUtil.fieldByName(columnNm).asString());
                    }

                    jsonArr.add(jsonDetail);

                    dbUtil.next();
                }

                jsonMain.add(tableName, jsonArr);
            }

            return jsonMain;
        }
    }
    //endregion

    //region "etc Function"
    public static class commonFunc {
        public static String NVL(String str, String strReturn) {
            if (TextUtils.isEmpty(str) || str.toUpperCase().trim() == "NULL") {
                return strReturn;
            }

            return str;
        }
    }
    //endregion

    //endregion
}


