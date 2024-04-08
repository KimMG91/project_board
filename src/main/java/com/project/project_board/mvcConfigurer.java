package com.project.project_board;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // addMapping("/**")은 모든 요청에 대해 CORS를 활성화한다는 것을 의미합니다. 엔드포인트에서 CORS가 허용 됩니다.
                .allowedOriginPatterns()
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
