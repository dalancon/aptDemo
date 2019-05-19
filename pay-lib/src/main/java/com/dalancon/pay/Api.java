package com.dalancon.pay;

/**
 * Created by dalancon on 2019/5/18.
 */

public class Api {
    private static Api api;

    public static ApiService service;

    private Api() {

    }

    public static Api getInstance() {
        synchronized (Api.class) {
            if (api == null)
                api = new Api();
        }
        return api;
    }


}
