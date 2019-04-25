package com.mo.kpay5;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;

/**
 * @ author：mo
 * @ data：2019/4/16:14:42
 * @ 功能：微信支付
 */
public class WxPayUtil {

    /**
     * 异步调用方法
     */
    private static void actionPay(Runnable runnable) {
        // 必须异步调用
        Thread payThread = new Thread(runnable);
        payThread.start();
    }

    /**
     * 微信支付
     * 参数  尤其是签名一定是后台给，否则不安全
     *
     * @param mActivity 环境
     * @param APP_ID    在开发平台申请的AppID
     * @param appid     应用ID  微信开放平台审核通过的应用APPID
     * @param partnerid 商户号    微信支付分配的商户号
     * @param prepayid  预支付交易会话ID   微信返回的支付交易会话ID
     * @param noncestr  随机字符串   随机字符串，不长于32位。
     * @param timestamp 时间戳
     * @param packag    扩展字段    暂填写固定值Sign=WXPay
     * @param sign      签名
     */
    public static void payModle(Activity mActivity, String APP_ID, final String appid, final String partnerid, final String prepayid, final String noncestr
            , final String timestamp, final String packag, final String sign) {
        if (TextUtils.isEmpty(APP_ID) || TextUtils.isEmpty(appid) || TextUtils.isEmpty(partnerid) || TextUtils.isEmpty(prepayid) || TextUtils.isEmpty(noncestr)
                || TextUtils.isEmpty(timestamp) || TextUtils.isEmpty(packag) || TextUtils.isEmpty(sign)) {
            Log.i("微信支付参数不对", "");
            Toast.makeText(mActivity, "请 核对请求参数！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        final IWXAPI api = WXAPIFactory.createWXAPI(mActivity, APP_ID);
        // 将应用的appId注册到微信
        boolean registerApp = api.registerApp(APP_ID);
        if (registerApp) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    PayReq req = new PayReq();
                    try {
                        req.appId = new String(Base64.decode(appid), "UTF-8");
                        req.partnerId = new String(Base64.decode(partnerid), "UTF-8");
                        req.prepayId = new String(Base64.decode(prepayid), "UTF-8");
                        req.nonceStr = new String(Base64.decode(noncestr), "UTF-8");
                        req.timeStamp = new String(Base64.decode(timestamp), "UTF-8");
                        req.packageValue = new String(Base64.decode(packag), "UTF-8");
                        req.sign = new String(Base64.decode(sign), "UTF-8");
                        api.sendReq(req);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            };
            actionPay(runnable);

        }

    }
}
