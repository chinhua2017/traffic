package com.cooee.traffic;

/**
 * 用于回调加载WebView的接口，包括支付结果回调和请求联系人信息.
 */

public interface IResult {
    /**
     * 支付结果回调
     * @param payResult JSON字符串
     */
    public abstract void onPayResult(String payResult);

    /**
     * 请求获取联系人信息
     * @param pickContactResult 联系人回调接口
     */
    public abstract void onPickContact(IContactResult pickContactResult);
}
