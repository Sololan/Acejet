package com.wejoy.wejoycms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Value("${picturepath.db-url}")
    private String dbUurl;

    @Value("${picturepath.loading-url}")
    private String loadingUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //上传的图片在D盘下的OTA目录下，访问路径如：http://localhost:8081/OTA/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //其中OTA表示访问的前缀。"file:D:/OTA/"是文件真实的存储路径
        registry.addResourceHandler(dbUurl + "**").addResourceLocations("file:" + loadingUrl);
    }
//    @Override
//    public void addViewControllers(ViewControllerRegistry viewControllerRegistry){
//        viewControllerRegistry.addViewController("/").setViewName("/web/index");
//        //设置ViewController的优先级,将此处的优先级设为最高,当存在相同映射时依然优先执行
//        viewControllerRegistry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        super.addViewControllers(viewControllerRegistry);
//    }
}
