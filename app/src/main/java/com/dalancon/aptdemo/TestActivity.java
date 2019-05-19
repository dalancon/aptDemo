package com.dalancon.aptdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.dalancon.annotations.ApiFactory;
import com.dalancon.annotations.BindView;
import com.dalancon.annotations.OnClick;

/**
 * Created by dalancon on 2019/5/18.
 */
public class TestActivity extends AppCompatActivity {
//
//
    @BindView(R.id.tv1)
    TextView tv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestActivity_ViewBinding viewBinding = new TestActivity_ViewBinding();
        viewBinding.bind(this);

        tv1.setText("dalancon butterknife");
//        Api.getInstance().service.getTransactionFee("https://www.baidu.com");
    }

    @OnClick(R.id.tv1)
    void click() {
        Toast.makeText(this, "click.....", Toast.LENGTH_SHORT).show();
    }
}
