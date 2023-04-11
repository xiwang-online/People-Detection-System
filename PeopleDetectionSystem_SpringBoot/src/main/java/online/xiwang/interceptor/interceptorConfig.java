package online.xiwang.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class interceptorConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new detectionInterceptor()).addPathPatterns("/api/detection/**");       //如果是detection的请求，则只要登录的用户都可以访问就可以看, 人数更新的方法不登陆也可以访问
            registry.addInterceptor(new cameraInterceptor()).addPathPatterns("/api/camera/**");       //如果是camera的请求，管理员可访问所有方法，访客只能访问查看的方法
            registry.addInterceptor(new userInterceptor()).addPathPatterns("/api/user/**");       //如果是user的请求，未登录的可以访问登录方法，管理员可以访问所有方法，访客可以访问查看的方法pei'zhi

        }

}
