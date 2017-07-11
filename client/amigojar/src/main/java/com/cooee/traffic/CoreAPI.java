package com.cooee.traffic;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by on 2017/6/19.
 */

public class CoreAPI {

    private Activity activity;
    private WebView webView;
    private IResult iResult;
    private MyAmigoPay amigoPay;

    /**
     * 加载URL页面
     * @param activity activity对象
     * @param webview webview对象
     * @param iResult 回调
     */
    public void load(Activity activity, WebView webview, IResult iResult) {
        Utils.v("load");
        this.activity = activity;
        this.webView = webview;
        this.iResult = iResult;

        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCachePath(activity.getApplicationContext().getCacheDir().getAbsolutePath());
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAppCacheEnabled(true);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(this, "webviewBuy");
        webview.loadUrl("http://www.kuyuad.com:8082/static/h5/?channel=LLGM");
    }

    protected void pickContactResult(final String name, final String phoneNumber) {
        Utils.v("pickContactResult: " + name + ", " + phoneNumber);
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:pickContactResult(\"" + name + "\",\"" + phoneNumber + "\")");
            }
        });
    }

    private void payEnd(final String result) {
        Utils.v("payEnd: " + result);
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:payEnd(\"" + result + "\")");
            }
        });
    }

    @JavascriptInterface
    public void pay(String param) {
        Utils.v("pay: " + param);

        try {
            JSONObject jo = new JSONObject(param);
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId((String)jo.get("orderId"));
            orderInfo.setPhone((String)jo.get("phone"));
            orderInfo.setTitle((String)jo.get("title"));
            orderInfo.setDesc((String)jo.get("desc"));
            double price = jo.optInt("price", -1);
            if(price > 0)orderInfo.setPrice(price);
            else orderInfo.setPrice((Double)jo.get("price"));
            double originalPrice = jo.optInt("originalPrice", -1);
            if(originalPrice > 0)orderInfo.setOriginalPrice(originalPrice);
            else orderInfo.setOriginalPrice((Double)jo.get("originalPrice"));
//            orderInfo.setStatusDesc((String)jo.get("statusDesc"));
//            orderInfo.setStatusCode((Integer)jo.get("statusCode"));
            orderInfo.setPayType((Integer)jo.get("payType"));
            orderInfo.setOperator((String)jo.get("operator"));
            orderInfo.setProductRange((String)jo.get("productRange"));
//            orderInfo.setPayTime((String)jo.get("payTime"));
            orderInfo.setAmigoApiKey((String)jo.get("amigoApiKey"));
            orderInfo.setAmigoOutOrderNo((String)jo.get("amigoOutOrderNo"));
            orderInfo.setAmigoSubmitTime((String)jo.get("amigoSubmitTime"));

            amigoPay = new MyAmigoPay(orderInfo.getPayType());
            amigoPay.pay(activity, orderInfo, new PayCB(orderInfo));
        } catch (Exception e) {
            e.printStackTrace();
            payEnd("fail");
        }
    }

    private void payResult(OrderInfo orderInfo) {
        Utils.v("payResult");
        try {
            JSONObject jo = new JSONObject();
            jo.put("orderId", orderInfo.getOrderId());
            jo.put("phone", orderInfo.getPhone());
            jo.put("title", orderInfo.getTitle());
            jo.put("desc", orderInfo.getDesc());
            jo.put("price", orderInfo.getPrice());
            jo.put("originalPrice", orderInfo.getOriginalPrice());
            jo.put("statusDesc", orderInfo.getStatusDesc());
            jo.put("statusCode", orderInfo.getStatusCode());
            jo.put("payType", orderInfo.getPayType());
            jo.put("operator", orderInfo.getOperator());
            jo.put("productRange", orderInfo.getProductRange());
            jo.put("payTime", orderInfo.getPayTime());

            Utils.v("payResult: " + jo.toString());

            iResult.onPayResult(jo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void pickContact() {
        Utils.v("pickContact");
        iResult.onPickContact(new ContactResult(this));
    }

    class ContactResult implements IContactResult {
        private CoreAPI coreAPI;

        public ContactResult(CoreAPI coreAPI) {
            this.coreAPI = coreAPI;
        }

        @Override
        public void pickContactResult(String name, String phoneNumber) {
            if(!Utils.isStringEmpty(name) && !Utils.isStringEmpty(phoneNumber)) {
                coreAPI.pickContactResult(name.trim(), Utils.preparePhoneNumber(phoneNumber));
            }
        }
    }

    class PayCB implements PayCallBack {

        private OrderInfo orderInfo;

        public PayCB(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        @Override
        public void onSuccess() {
            payEnd("suc");
            String payTime = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = format.parse(orderInfo.getAmigoSubmitTime());
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                payTime = format.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(Utils.isStringEmpty(payTime)) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    payTime = format.format(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(Utils.isStringEmpty(payTime)) {
                payTime = "2017-01-01 00:00:00";
            }

            orderInfo.setPayTime(payTime);
            orderInfo.setStatusCode(OrderInfo.STATUS_CODE_PAY_SUC);
            orderInfo.setStatusDesc(Constants.PAY_SUC);
            payResult(orderInfo);
        }
        @Override
        public void onFail(int code, String msg) {
            payEnd("fail");
        }
    }
}
