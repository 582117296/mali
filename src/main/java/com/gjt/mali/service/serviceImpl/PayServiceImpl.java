package com.gjt.mali.service.serviceImpl;

import com.alipay.api.AlipayApiException;
import com.gjt.mali.pojo.AlipayBean;
import com.gjt.mali.service.PayService;
import com.gjt.mali.utils.Alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private Alipay alipay;

	@Override
	public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
		return alipay.pay(alipayBean);
	}

}
