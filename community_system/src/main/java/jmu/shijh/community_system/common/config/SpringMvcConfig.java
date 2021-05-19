package jmu.shijh.community_system.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import jmu.shijh.community_system.common.MultiRequestBodyArgumentResolver;
import jmu.shijh.community_system.common.util.Cl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    MultiRequestBodyArgumentResolver multiRequestBodyArgumentResolver;

    private FastJsonHttpMessageConverter fastjson() {
        FastJsonHttpMessageConverter fjc = new FastJsonHttpMessageConverter();
        fjc.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        fjc.setDefaultCharset(StandardCharsets.UTF_8);
        fjc.setFastJsonConfig(new FastJsonConfig() {{
            setDateFormat("yyyy-MM-dd");
            setSerializerFeatures(
                    SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.WriteDateUseDateFormat
            );
        }});
        return fjc;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * 配置 resolver 支持多个RequestBody解析
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(multiRequestBodyArgumentResolver);
    }

    /**
     * 配置前后端跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, fastjson());
    }
}
