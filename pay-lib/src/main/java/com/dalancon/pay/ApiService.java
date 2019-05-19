package com.dalancon.pay;

import com.dalancon.annotations.ApiFactory;

import java.util.Map;

/**
 * Created by dalancon on 2019/5/18.
 */

@ApiFactory
public interface ApiService {

    String sendTx(String url);

    String receive(String url, String params);

    String payment(String url);
}
