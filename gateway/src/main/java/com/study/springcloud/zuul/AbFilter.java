package com.study.springcloud.zuul;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author mushui
 * @created 2017-05-08 下午3:56
 */
@Component
public class AbFilter extends ZuulFilter {
	private static Logger log = LoggerFactory.getLogger(AbFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 10;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String isTest = request.getParameter("test");
		if ("1".equals(isTest)) {
			ctx.put("serviceId", ctx.get("serviceId") + "-test");
			log.info("rewrite to test");
		} else if ("2".equals(isTest)){
			try {
				ctx.setRouteHost(new URL("http://localhost:8044"));
				log.info("rewrite to test");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
