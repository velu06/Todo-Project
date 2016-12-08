package com.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.todo.controller")
public class WebSpringConfig extends WebMvcConfigurerAdapter 
{
	
	@Bean
	public ViewResolver configureViewResolver() {
	    InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
	    viewResolve.setPrefix("/jsp/");
	    viewResolve.setSuffix(".jsp");
	    return viewResolve;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
	  configurer.enable();
	}
}