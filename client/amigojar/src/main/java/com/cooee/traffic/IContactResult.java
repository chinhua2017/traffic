package com.cooee.traffic;

/**
 * Created by on 2017/6/21.
 */

public interface IContactResult {
    /**
     * 返回联系人信息
     * @param name 名字
     * @param phoneNumber 电话号码
     */
    public abstract void pickContactResult(String name, String phoneNumber);
}
