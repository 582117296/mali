package com.gjt.mali.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 配置文件监听器，用来加载自定义配置文件
 * @author GJT
 * @date 2019-09-26 11:19:44
 */
@Component
public class PropertiesListener implements ApplicationListener<ApplicationStartedEvent> {
	@Override
	public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
		AlipayProperties.loadProperties();
	}
}
