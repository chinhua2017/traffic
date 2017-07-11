package com.cooee.traffic;

/**
 * Created by on 2017/6/21.
 */

public interface PayCallBack {

    public void onSuccess();

    public void onFail(int code, String msg);
}
