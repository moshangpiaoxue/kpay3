package mo.kpay.ccb;

import java.io.Serializable;

/**
 * @ author：mo
 * @ data：2019/4/22：14:07
 * @ 功能：商户参数
 */
public class OrderInfoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	* 商户编号
	*/
	private String merchantId;
	/**
	* 柜台编号
	*/
	private String posId;
	/**
	* 分行编号
	*/
	private String bankId;
	/**
	* 30位的商户公钥
	*/
	private String pubNo;
	/**
	* 分期数
	*/
	private String installNum="";

	public OrderInfoBean() {
	}

	public OrderInfoBean(String merchantId, String posId, String bankId, String pubNo) {
		this.merchantId = merchantId;
		this.posId = posId;
		this.bankId = bankId;
		this.pubNo = pubNo;
	}

	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getPubNo() {
		return pubNo;
	}
	public void setPubNo(String pubNo) {
		this.pubNo = pubNo;
	}
	public String getInstallNum() {
		return installNum;
	}
	public void setInstallNum(String installNum) {
		this.installNum = installNum;
	}

}
