package com.mo.kpay4;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.ccb.ccbnetpay.CcbMorePay;
import com.ccb.ccbnetpay.message.CcbPayResultListener;
import com.ccb.ccbnetpay.platform.CcbPayAliPlatform;
import com.ccb.ccbnetpay.platform.CcbPayPlatform;
import com.ccb.ccbnetpay.platform.CcbPayWechatPlatform;
import com.ccb.ccbnetpay.platform.Platform;
import com.ccb.ccbnetpay.util.IPUtil;
import com.mo.kpay4.ccb.OrderInfoBean;
import com.mo.kpay4.ccb.UrlTest;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @ author：mo
 * @ data：2019/4/22:13:45
 * @ 功能：建行支付工具类
 */
public class CcbPayUtil {
    /**
     * 建行支付==综合支付（通过建行弹支付宝、微信等）
     *
     * @param mActivity   载体
     * @param weiXinAppId 微信appid （可空，但是当选择了微信支付之后会报错）
     * @param busInfo     商户参数
     * @param price       价格
     * @param listener    回调监听
     */
    public static void payZongHe(Activity mActivity, String weiXinAppId, OrderInfoBean busInfo, String price, CcbPayResultListener listener) {
        boolean registerApp = registWx(mActivity, weiXinAppId);
        UrlTest urlTest = new UrlTest();
        String params = urlTest.make(busInfo, price, IPUtil.getIPAddress());
        Log.i("建行支付参数==", params);
        //SDK综合支付
        CcbMorePay.getInstance().pay(mActivity, params, listener);
    }

    /**
     * 建行支付== app支付 （使用建行app支付）
     */
    public static void payApp(Activity mActivity, OrderInfoBean busInfo, String price, CcbPayResultListener listener) {
        pay(mActivity, busInfo, price, Platform.PayStyle.APP_PAY, listener);
    }

    /**
     * 建行支付== h5支付 （使用建行H5界面支付）
     */
    public static void payH5(Activity mActivity, OrderInfoBean busInfo, String price, CcbPayResultListener listener) {
        pay(mActivity, busInfo, price, Platform.PayStyle.H5_PAY, listener);
    }

    /**
     * 建行支付== app或者H5支付 （使用建行app就使app支付，没有就用H5支付）
     */
    public static void payAppOrH5(Activity mActivity, OrderInfoBean busInfo, String price, CcbPayResultListener listener) {
        pay(mActivity, busInfo, price, Platform.PayStyle.APP_OR_H5_PAY, listener);
    }

    /**
     * 建行支付== 微信支付 （注意注册微信appid）
     */
    public static void payWx(Activity mActivity, String weiXinAppId, OrderInfoBean busInfo, String price, CcbPayResultListener listener) {
        boolean registerApp = registWx(mActivity, weiXinAppId);
        UrlTest urlTest = new UrlTest();
        //组合参数
        String params = urlTest.make(busInfo, price, IPUtil.getIPAddress());
        Log.i("建行微信支付参数==", params);

        Platform ccbPayPlatform = new CcbPayWechatPlatform
                .Builder()
                .setActivity(mActivity)
                //  支付回调
                .setListener(listener)
                // 商户串
                .setParams(params)
                .build();
        //开始支付
        ccbPayPlatform.pay();
    }

    /**
     * 建行支付== 支付宝支付
     */
    public static void payAli(Activity mActivity, OrderInfoBean busInfo, String price, CcbPayResultListener listener) {
        UrlTest urlTest = new UrlTest();
        //组合参数
        String params = urlTest.make(busInfo, price, IPUtil.getIPAddress());
        Log.i("建行支付宝支付参数==", params);

        Platform aLi = new CcbPayAliPlatform
                .Builder()
                .setActivity(mActivity)
                //  支付回调
                .setListener(listener)
                // 商户串
                .setParams(params)
                .build();
        //开始支付
        aLi.pay();
    }

    /**
     * 建行支付
     *
     * @param mActivity 载体
     * @param busInfo   商户参数
     * @param price     价格
     * @param style     支付类型
     * @param listener  回调
     */
    private static void pay(Activity mActivity, OrderInfoBean busInfo, String price, Platform.PayStyle style, CcbPayResultListener listener) {
        UrlTest urlTest = new UrlTest();
        //组合参数
        String params = urlTest.make(busInfo, price, IPUtil.getIPAddress());
        Log.i("建行支付参数==", params);

        Platform ccbPayPlatform = new CcbPayPlatform
                .Builder()
                .setActivity(mActivity)
                //  支付回调
                .setListener(listener)
                // 商户串
                .setParams(params)
                // 支付模式
                .setPayStyle(style)
                .build();
        //开始支付
        ccbPayPlatform.pay();
    }

    /**
     * 注册微信
     *
     * @param mActivity
     * @param weiXinAppId
     * @return
     */
    private static boolean registWx(Activity mActivity, String weiXinAppId) {
        if (!TextUtils.isEmpty(weiXinAppId)) {
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            final IWXAPI api = WXAPIFactory.createWXAPI(mActivity, weiXinAppId);
            // 将应用的appId注册到微信
            return api.registerApp(weiXinAppId);
        } else {
            return false;
        }

    }
}
