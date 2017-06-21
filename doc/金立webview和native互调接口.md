### 金立webview和native互调接口

#### 1. native设置JS接口

```java
webview.addJavascriptInterface(this, "webviewBuy");
```

#### 2. 联系人接口调用

- js 调用 native 方法

```javascript
window.webviewBuy.pickContact()
```

- native 回调 js 方法

```java
webView.loadUrl("javascript:pickContactResult(\"" + name + "\",\"" + phoneNumber + "\")");
```

#### 3. 计费接口调用

- js 调用 native 方法

```javascript
window.webviewBuy.pay("json")
```

json参数定义：
| value | type | desc | exp |
| ----- | ---- | ---- | --- |
| orderId | string | 自有订单号 | |
| phone | string | 电话 | |
| title | string | 标题 | 30M |
| price | float | 卖出价 | 2.85 |
| originalPrice | float | 官方价 | 3 |
| payType | int | 支付类型 | 0: 支付宝，1：微信 |
| operator | string | 运营商 | 移动、电信、联通 |
| productRange | string | 产品有效范围 | 全国、北京、上海 |
| amigoApiKey | string | 金立API KEY | BASE32 string |
| amigoOutOrderNo | string | 金立商户订单号 | |
| amigoSubmitTime | string | 订单提交时间 | yyyyMMddHHmmss |

- native 回调 js 方法

```java
webView.loadUrl("javascript:payEnd(\"" + result + "\")");
```

result定义:
| value | desc |
| ----- | ---- |
| suc   | 支付成功 |
| fail  | 支付失败 |
