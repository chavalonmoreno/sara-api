package com.example.sara;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@ConfigurationProperties(prefix = "front-end")
public class CorsConfig  {

    public String getFrontEndHost() {
        return frontEndHost;
    }

    public void setFrontEndHost(String frontEndHost) {
        this.frontEndHost = frontEndHost;
    }

    private String frontEndHost;

    @Bean
    public FilterRegistrationBean corsFilter() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(this.getFrontEndHost());
        config.addAllowedOrigin("http://127.0.0.1");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}

