package com.dalancon.aptdemo;

import com.dalancon.annotations.ApiFactory;

import java.util.Map;

/**
 * Created by dalancon on 2019/5/18.
 */

@ApiFactory
public interface ApiService {

    String getTransactionFee(String url);

    String login(String url, Map<String, String> params);

    String logout(String url);
}
