package com.gjt.mali.service;

import com.alipay.api.AlipayApiException;
import com.gjt.mali.pojo.AlipayBean;

public interface PayService {

	/**
	 * 支付宝支付接口
	 * @param alipayBean
	 * @return
	 * @throws AlipayApiException
	 */
	String aliPay(AlipayBean alipayBean) throws AlipayApiException;

}
