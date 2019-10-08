package com.gjt.mali.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gjt.mali.config.AlipayProperties;
import com.gjt.mali.pojo.AlipayBean;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付接口
 * @author Louis
 * @date Dec 12, 2018
 */
@Component
public class Alipay {
	public String pay(AlipayBean alipayBean) throws AlipayApiException {
		String serverUrl = AlipayProperties.getGatewayUrl();
		String appId = AlipayProperties.getAppId();
		String charset = AlipayProperties.getCharset();
		String logPath = AlipayProperties.getLogPath();
		String format = "json";
		String notifyUrl = AlipayProperties.getNotifyUrl();
		String privateKey = AlipayProperties.getPrivateKey();
		String publicKey = AlipayProperties.getPublicKey();
		String signType = AlipayProperties.getSignType();
		String returnUrl = AlipayProperties.getReturnUrl();


		AlipayClient alipayClient=new DefaultAlipayClient(serverUrl,appId,privateKey,format,charset,publicKey,signType);
//		设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		// 页面跳转同步通知页面路径
		alipayRequest.setReturnUrl(returnUrl);
		// 服务器异步通知页面路径
		alipayRequest.setNotifyUrl(notifyUrl);
		// 封装参数
		alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
		// 3、请求支付宝进行付款，并获取支付结果
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		return result;
	}


}
