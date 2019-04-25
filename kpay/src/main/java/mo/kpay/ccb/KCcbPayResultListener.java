package mo.kpay.ccb;

import com.ccb.ccbnetpay.message.CcbPayResultListener;

import java.util.Map;

/**
 * @ author：mo
 * @ data：2019/4/22:15:19
 * @ 功能：建行支付回调
 */
public class KCcbPayResultListener implements CcbPayResultListener {
    /**
     * 成功
     */
    @Override
    public void onSuccess(Map<String, String> map) {

    }

    /**
     * 失败
     */
    @Override
    public void onFailed(String s) {

    }
}
