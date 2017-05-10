package com.study.springcloud.zuul;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 使用@EnableZuulProxy注解激活zuul。
 * 跟进该注解可以看到该注解整合了@EnableCircuitBreaker、@EnableDiscoveryClient，是个组合注解，目的是简化配置。
 * @author eacdy
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApiGatewayApplication {
  public static void main(String[] args) {
      FilterLoader.getInstance().setCompiler(new GroovyCompiler());
      try {
          FilterFileManager.setFilenameFilter(new GroovyFileFilter());
          FilterFileManager.init(10,"/Users/mushui/zuul-filter");
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
      SpringApplication.run(ZuulApiGatewayApplication.class, args);
  }
}