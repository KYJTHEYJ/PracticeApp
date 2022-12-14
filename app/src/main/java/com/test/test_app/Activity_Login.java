package com.test.test_app;

import static com.test.test_app.base.BaseApplication.dbUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.test.test_app.base.BaseActivity;
import com.test.test_app.base.BaseApplication;
import com.test.test_app.databinding.ActivityMainBinding;
import com.test.test_app.login.loginService;
import com.test.test_app.network.HttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rxdogtag2.RxDogTag;

public class Activity_Login extends BaseActivity {

    private ActivityMainBinding binding;

    // RxJava - AsyncTask
    private Disposable backGroundTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initListener();
    }

    private void initListener() {
        binding.btn.setOnClickListener(onClickListener);
        binding.btnAsync.setOnClickListener(onClickListener);

        binding.btnInsert.setOnClickListener(onClickListener);
        binding.btnDelete.setOnClickListener(onClickListener);
        binding.btnUpdate.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn :
                    // COMP_CD -> 10 (yjyj)
                    login("10", binding.edtId.getText().toString(), binding.edtPw.getText().toString()); // ?????????
                    break;
                case R.id.btn_async:
                    AsyncTaskTest();
                    break;
                case R.id.btn_insert:
                    insertChk();
                    break;
            }
        }
    };

    private void login(String comp_cd, String id, String pw) {
        BaseApplication.Send.SendParams sendParams = new BaseApplication.Send.SendParams();

        sendParams.sendParams.put("COMP_CD", "10");
        sendParams.sendParams.put("ID", id);
        sendParams.sendParams.put("PW", pw);

        //Queue ????????? Retrofit
        //Response??? ???????????? ????????? ?????? ????????? ??? ?????? ???????????? ???????????? (Successful?????? Failure)
        //?????? ???????????? ?????????, Main Thread?????? ?????? ??? ??????.
        /*
        BaseApplication.setProgressDialog.setProgressDialog("????????? ?????? ?????? ???");

        //Retrofit
        loginService loginService = HttpClient.getInstance().create(com.test.test_app.login.loginService.class);
        Call<String> call = loginService.login(BaseApplication.Send.changeToJsonObj(sendParams));

        call.enqueue(new Callback<String> () {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    Log.d("yjyj", "login1: " + Thread.currentThread().getName());
                    Log.d("yjyj", "login_onResponse: " + response.body() + " / " + response.code());

                    try {
                        //response?????? Json ???????????? ????????? -> row ?????? ???????????? ?????????, String -> JSONObject??? ???????????????.
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject rowData = jsonObject.getJSONArray("USER_INFO").getJSONObject(0);

                        Log.d("yjyj", "onResponse: " + rowData.getString("COMP_CD"));

                        BaseApplication.setProgressDialog.setProgressDialogDissmiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("yjyj", "login2: " + Thread.currentThread().getName());
                        BaseApplication.toast.showToast("????????? ??????");
                        BaseApplication.setProgressDialog.setProgressDialogDissmiss();
                    }
                } else {
                    Log.d("yjyj", "login3: " + Thread.currentThread().getName());
                    BaseApplication.toast.showToast("????????? ??????");
                    BaseApplication.setProgressDialog.setProgressDialogDissmiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("yjyj", "?????? ?????? : " + t.getMessage() );
                BaseApplication.setProgressDialog.setProgressDialogDissmiss();
            }
        });
        */

        //Retrofit
        //onPreExecute
        BaseApplication.setProgressDialog.setProgressDialog("????????? ?????? ?????? ???");

        loginService loginService = HttpClient.getInstance().create(com.test.test_app.login.loginService.class);
        Call<String> call = loginService.login(BaseApplication.Send.changeToJsonObj(sendParams));

        //Error ??????
        RxDogTag.install();
        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.e("yjyj", throwable.getMessage());
            BaseApplication.setProgressDialog.setProgressDialogDissmiss();
        });

        backGroundTask = Observable.fromCallable(() -> {
            //doInBackground
            try {
                Response<String> response = call.execute();

                //response?????? Json ???????????? ????????? -> row ?????? ???????????? ?????????, String -> JSONObject??? ???????????????.
                JSONObject jsonObject = new JSONObject(response.body());
                JSONObject rowData = jsonObject.getJSONArray("USER_INFO").getJSONObject(0);

                Log.d("yjyj", "onResponse: " + rowData.getString("COMP_CD"));

                BaseApplication.setProgressDialog.setProgressDialogDissmiss();
            } catch (Exception e) {
                e.printStackTrace();
                BaseApplication.setProgressDialog.setProgressDialogDissmiss();
                return false;
            }

            return true;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((result) -> {
            //onPostExecute
            BaseApplication.setProgressDialog.setProgressDialogDissmiss();

            if(result) {
                BaseApplication.toast.showToast("????????? ?????? + ????????? ????????? ??????");
            } else {
                BaseApplication.toast.showToast("????????? ?????? + ????????? ????????? ??????");
            }

            backGroundTask.dispose();
        });
    }

    private void insertChk() {
        BaseApplication.Send.SendParams sendParams = new BaseApplication.Send.SendParams();

        sendParams.sendParams.put("COMP_CD", "10");
        sendParams.sendParams.put("GBN_CD", "1");
        sendParams.sendParams.put("REF_CD", "1");
        sendParams.sendParams.put("REF_SNM", "1");
        sendParams.sendParams.put("REF_NM", "1");
        sendParams.sendParams.put("USE_YN", "N");

        BaseApplication.setProgressDialog.setProgressDialog("INSERT ????????? ???");

        //Retrofit
        loginService loginService = HttpClient.getInstance().create(com.test.test_app.login.loginService.class);
        Call<String> call = loginService.InsertChk(BaseApplication.Send.changeToJsonObj(sendParams));

        call.enqueue(new Callback<String> () {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    Log.d("yjyj", "login_onResponse: " + response.body() + " / " + response.code());

                    try {
                        //response?????? Json ???????????? ????????? -> row ?????? ???????????? ?????????, String -> JSONObject??? ???????????????.
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject rowData = jsonObject.getJSONArray("SUCCESS").getJSONObject(0);

                        Log.d("yjyj", "onResponse: " + rowData.getString("APP_MSG"));

                        BaseApplication.toast.showToast("INSERT ?????? : " + rowData.getString("APP_MSG"));
                        BaseApplication.setProgressDialog.setProgressDialogDissmiss();
                    } catch (JSONException e) {
                        e.printStackTrace();

                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONObject rowData = jsonObject.getJSONArray("ERROR").getJSONObject(0);

                            BaseApplication.toast.showToast("INSERT ?????? : " + rowData.getString("APP_MSG"));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        BaseApplication.setProgressDialog.setProgressDialogDissmiss();
                    }
                } else {
                    BaseApplication.toast.showToast("INSERT ??????");
                    BaseApplication.setProgressDialog.setProgressDialogDissmiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("yjyj", "?????? ?????? : " + t.getMessage() );
                BaseApplication.setProgressDialog.setProgressDialogDissmiss();
            }
        });
    }

    private void AsyncTaskTest() {
        //onPreExecute
        BaseApplication.setProgressDialog.setProgressDialog("????????? ?????? ????????? ???");

        //Error ??????
        RxDogTag.install();
        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.e("yjyj", throwable.getMessage());
            BaseApplication.setProgressDialog.setProgressDialogDissmiss();
        });

        backGroundTask = Observable.fromCallable(() -> {
            //doInBackground
            try {
                String sql = "INSERT INTO TEST_TEST ( " +
                        "  COMP_CD " +
                        ") VALUES ( " +
                        "  :COMP_CD " +
                        ") ";

                dbUtil.dbParams.clear();
                dbUtil.dbParams.add("COMP_CD", "TEST_1");
                dbUtil.execSql(sql);
            } catch (Exception e) {
                Log.e("yjyj", "AsyncTaskTest: " + e.getMessage() );
                return false;
            }

            return true;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((result) -> {
            //onPostExecute
            BaseApplication.setProgressDialog.setProgressDialogDissmiss();

            if(result) {
                BaseApplication.toast.showToast("????????? ?????? ????????? ??????");
            } else {
                BaseApplication.toast.showToast("????????? ?????? ????????? ??????");
            }

            backGroundTask.dispose();
        });
    }
}