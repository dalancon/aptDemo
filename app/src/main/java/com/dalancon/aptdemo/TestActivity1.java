package com.dalancon.aptdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dalancon.annotations.ApiFactory;
import com.dalancon.annotations.BindView;

/**
 * Created by dalancon on 2019/5/18.
 */
public class TestActivity1 extends AppCompatActivity {


    @BindView(R.id.tv1)
    TextView tv1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestActivity1_ViewBinding binding = new TestActivity1_ViewBinding();
        binding.bind(this);
        tv1.setText("hhhhhhhhhhh");
    }
}
