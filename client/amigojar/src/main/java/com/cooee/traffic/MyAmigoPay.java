package com.cooee.traffic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gionee.gsp.AmigoPayer;
import com.gionee.gsp.GnCommplatform;
import com.gionee.gsp.GnECustomChannel;
import com.gionee.gsp.GnEType;
import com.gionee.gsp.GnOrderInfo;
import com.gionee.gsp.service.GnCommplatformImplForBase;
import com.gionee.gsp.standalone.customchannel.GnCommplatformForCustomChannelStandalone;

/**
 * Created by on 2017/6/16.
 */

public class MyAmigoPay {
    private int payType;
    private AmigoPayer.MyPayCallback mMyPayCallback;
    private PayCallBack payCallBack;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (0 == msg.what) {
                String statusCode = (String)msg.obj;
                if(statusCode.equals("9000")) {
                    getPayCallBack().onSuccess();
                } else {
                    if(statusCode.equals("4000")) {
                        getPayCallBack().onFail(0, "系统异常");
                    } else if(statusCode.equals("4001")) {
                        getPayCallBack().onFail(0, "数据格式不正确");
                    } else if(statusCode.equals("4002")) {
                        getPayCallBack().onFail(0, "空间不足，升级 失败");
                    } else if(statusCode.equals("4003")) {
                        getPayCallBack().onFail(0, "账号安全级别低， 升级失败");
                    } else if(statusCode.equals("4004")) {
                        getPayCallBack().onFail(0, "传递给支付收银台显示的图片太大");
                    } else if(statusCode.equals("4006")) {
                        getPayCallBack().onFail(0, "订单支付失败");
                    } else if(statusCode.equals("6000")) {
                        getPayCallBack().onFail(0, "支付服务正在进行升级操作或异常");
                    } else if(statusCode.equals("6001")) {
                        getPayCallBack().onFail(0, "用户中途取消支付操作");
                    } else if(statusCode.equals("6002")) {
                        getPayCallBack().onFail(0, "网络连接异常");
                    } else {
                        getPayCallBack().onFail(0, null);
                    }
                }
            }
        }
    };

    public MyAmigoPay(int payType) {
        this.payType = payType;
    }

    protected void setPayCallBack(PayCallBack payCallBack) {
        this.payCallBack = payCallBack;
    }

    protected PayCallBack getPayCallBack() {
        return payCallBack;
    }

    public void pay(Activity activity, OrderInfo orderInfo, PayCallBack callBack) {
        Utils.v("Amigo pay start 17.07.11.11" );
        setPayCallBack(callBack);

        GnCommplatformForCustomChannelStandalone napi = GnCommplatformForCustomChannelStandalone.getInstance(activity);
        napi.init(activity, GnEType.NO_ACCOUNT_BY_CHANNEL, orderInfo.getAmigoApiKey(), "0");
        ((GnCommplatformImplForBase)GnCommplatformImplForBase.getInstance(activity)).setApiKey(orderInfo.getAmigoApiKey());

//        GnCommplatform.getInstance(activity).init(activity, GnEType.NO_ACCOUNT_BY_CHANNEL, orderInfo.getAmigoApiKey());

        AmigoPayer mAmigoPayer = new AmigoPayer(activity);

        mMyPayCallback = mAmigoPayer.new MyPayCallback() {
            /*
             * 支付结束
             *
             * @params stateCode 支付结果的返回码，代码成功还是失败 返回码定义，请见文档说明
             */
            @Override
            public void payEnd(final String stateCode) {
                // 这句话必须，否则apk升级后不会自动调起收银台
                super.payEnd(stateCode);
                Utils.v("Amigo: payEnd,stateCode = " + stateCode);
                if (isAppUpdate(stateCode)) {
                    return;
                }

                Message msg = new Message();
                msg.what = 0;
                msg.obj = stateCode;
                mHandler.sendMessage(msg);
            }

            private boolean isAppUpdate(String stateCode) {
                return AmigoPayer.RESULT_CODE_UPDATE.equals(stateCode)
                        || AmigoPayer.RESULT_CODE_UPDATE_IS_NOT_GIONEE.equals(stateCode);
            }
        };

        GnECustomChannel channel = GnECustomChannel.ALIPAY;
        if (payType == Constants.PAY_TYPE_WX) {
            channel = GnECustomChannel.WECHAT;
        }
        napi.pay(activity, new GnOrderInfo(orderInfo.getAmigoOutOrderNo(), orderInfo.getAmigoSubmitTime()), mMyPayCallback, channel, "");

//        Bundle bundlePara = new Bundle();
//        String channel = GnECustomChannel.ALIPAY.toString();
//        if (payType == Constants.PAY_TYPE_WX) {
//            channel = GnECustomChannel.WECHAT.toString();
//        }
//        bundlePara.putString("custom_channel", channel);
//        mAmigoPayer.pay(new GnOrderInfo(orderInfo.getAmigoOutOrderNo(), orderInfo.getAmigoSubmitTime()), mMyPayCallback, bundlePara);
    }

}
