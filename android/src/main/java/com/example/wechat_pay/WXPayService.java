package com.example.wechat_pay;


import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import io.flutter.plugin.common.MethodCall;

public class WXPayService {

    private static String TAG = "WXPayService";

    private static WXPayService instance;

    private WXPayService() {
    }

    private IWXAPI api;

    public static WXPayService getInstance() {
        if (instance == null)
            instance = new WXPayService();
        return instance;
    }

    public void initWXPayService(Context context) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, "wx5c685a6653728677", true);
            api.registerApp("wx5c685a6653728677");
        }
    }


    public void startWXPay(MethodCall call) {
        PayReq req = new PayReq();
        req.appId = call.argument("appId");//你的微信appid
        req.partnerId = call.argument("partnerId");// json.getString("partnerId");//商户号
        req.prepayId = call.argument("prepayId");// json.getString("prepayId");//预支付交易会话ID
        req.nonceStr = call.argument("nonceStr");// json.getString("nonceStr");//随机字符串
        req.timeStamp = call.argument("timeStamp");// json.getString("timestamp");//时间戳
        req.packageValue = call.argument("packageValue");// json.getString("package");//扩展字段,这里固定填写Sign=WXPay
        req.sign = call.argument("sign");// json.getString("sign");//签名
        api.sendReq(req);
    }


}
