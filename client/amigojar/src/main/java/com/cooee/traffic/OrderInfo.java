package com.cooee.traffic;

/**
 * Created by on 2017/6/21.
 */

public class OrderInfo {

    public static final int STATUS_CODE_RECHARGE_ING = 30;//充值中
    public static final int STATUS_CODE_RECHARGE_FAIL = 32;//充值失败
    public static final int STATUS_CODE_RECHARGE_SUC = 39;//充值成功
    public static final int STATUS_CODE_REFUND_SUC = 59;//退款成功
    public static final int STATUS_CODE_PAY_SUC = 100;//支付成功
    public static final int STATUS_CODE_PAY_FAIL = 101;//支付失败
    public static final int STATUS_CODE_ORDER_ERR = 102;//提交金立支付订单错误

    // 订单号
    private String orderId;
    // 电话号码
    private String phone;
    // 流量包标题 exp. 30M
    private String title;
    // 描述信息 exp. 全国可用，当月有效，月底失效
    private String desc;
    // 实际购买价格 exp. 2.85
    private Double price;
    // 标准价格，运营商官方价格 exp. 3
    private Double originalPrice;
    // 订单状态描述 exp. 付费成功
    private String statusDesc;
    // 订单状态
    private int statusCode;
    // 支付方式 0: 支付宝 1: 微信
    private int payType;
    // 运营商 移动、电信、联通
    private String operator;
    // 产品有效范围 exp. 全国、北京、上海
    private String productRange;
    // 支付时间 exp. 2016-10-01 19:20:30
    private String payTime;
    // Amigo API KEY
    private String amigoApiKey;
    // Amigo 商户订单号
    private String amigoOutOrderNo;
    // Amigo 订单提交时间 exp. yyyyMMddHHmmss
    private String amigoSubmitTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getProductRange() {
        return productRange;
    }

    public void setProductRange(String productRange) {
        this.productRange = productRange;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAmigoApiKey() {
        return amigoApiKey;
    }

    public void setAmigoApiKey(String amigoApiKey) {
        this.amigoApiKey = amigoApiKey;
    }

    public String getAmigoOutOrderNo() {
        return amigoOutOrderNo;
    }

    public void setAmigoOutOrderNo(String amigoOutOrderNo) {
        this.amigoOutOrderNo = amigoOutOrderNo;
    }

    public String getAmigoSubmitTime() {
        return amigoSubmitTime;
    }

    public void setAmigoSubmitTime(String amigoSubmitTime) {
        this.amigoSubmitTime = amigoSubmitTime;
    }
}
