package com.example.emailwarningdemo.Excetion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 *  * @author wangjiuzhou (835540436@qq.com)
 *  * @date 2018/11/07
 *  *
 *  * 获取当前项目环境：local、dev、test、pro
 *  
 */
@Configuration
public class ProfileConfig {
    public static final String DEV_PROFILE = "dev";
    public static final String TEST_PROFILE = "test";
    public static final String PRO_PROFILE = "pro";

    @Autowired
    private ApplicationContext context;

    public String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }
}
