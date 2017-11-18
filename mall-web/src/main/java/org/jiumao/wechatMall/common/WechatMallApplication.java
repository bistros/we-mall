package org.jiumao.wechatMall.common;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.jiumao.wechatMall.common.app.AuthServiceFeature;
import org.jiumao.wechatMall.common.app.CORSResponseFilter;
import org.jiumao.wechatMall.common.app.LoggingResponseFilter;

/**
 * Register JAX-RS application components.
 */
public class WechatMallApplication extends ResourceConfig {

	public WechatMallApplication() {

		String admin = "org.jiumao.wechatMall.admin.resource";
		String mall = "org.jiumao.wechatMall.mall.resource";
		String api = "org.jiumao.wechatMall.api.resource";
		String user = "org.jiumao.wechatMall.user.resource";
		packages(admin, mall, api, user);
		//org.glassfish.jersey.server.spring.scope.RequestContextFilter 是 Spring filter 提供了 JAX-RS 和 Spring 请求属性之间的桥梁。
		register(RequestContextFilter.class);
		//org.glassfish.jersey.jackson.JacksonFeature,是一个 feature ，用 Jackson JSON 的提供者来解释 JSON。
		register(JacksonFeature.class);
		register(AuthServiceFeature.class);
		register(LoggingResponseFilter.class);
		register(CORSResponseFilter.class);
		
		
	}
}