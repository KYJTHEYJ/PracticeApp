package com.test.test_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.test.test_app.databinding.ActivityMainBinding;

import java.util.HashMap;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class Activity_Login extends AppCompatActivity {

    private ActivityMainBinding binding;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initListener();
    }

    private void initListener() {
        binding.btn.setOnClickListener(m_OnClickListener);
    }

    View.OnClickListener m_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn :
                    // COMP_CD -> 10 (BABA)
                    login("10", binding.edtId.getText().toString(), binding.edtPw.getText().toString()); // 로그인
                    break;
            }
        }
    };

    private void login(String compCd, String iD, String passWord) {
        //파라미터를 맵에 담고 JSON 형태로 변환하여 넘긴다
        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put("COMP_CD", compCd);
        paramMap.put("ID", iD);
        paramMap.put("PW", passWord);


    }
}