package org.esupportail.esupnfccarteculture.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.annotation.Resource;

@Configuration
@PropertySource("classpath:META-INF/spring/database.properties")
@EnableWebMvc
@ComponentScan
@EnableAutoConfiguration
public class AppConfig implements WebMvcConfigurer {

    @Resource
    protected ApplicationContext applicationContext;

    @Resource
    protected SpringTemplateEngine springTemplateEngine;

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**","/webjars/**")
                .addResourceLocations("classpath:/", "classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public UrlBasedViewResolver tilesViewResolver(){
        UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        return tilesViewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer(){
        String[] definitions = new String[] {
                "/WEB-INF/views/jsp/layouts/layouts.xml",
                "/WEB-INF/views/**/views.xml"
        };
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(definitions);
        return tilesConfigurer;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(){
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setViewNames(new String[] {"thymeleaf/*"});
        viewResolver.setExcludedViewNames(new String[] {"jsp/*"});
        viewResolver.setTemplateEngine(springTemplateEngine);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(){
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setOrder(0);
        return templateResolver;
    }

}