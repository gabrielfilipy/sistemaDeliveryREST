package com.sistema.delivery.api.core.web;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sistema.delivery.api.v1.SistemaLink;

@Configuration
public class WebConfig implements WebMvcConfigurer{


	@Bean
	public Filter shallowEtagHeaderFilter()
	{
		return new ShallowEtagHeaderFilter();
	}
	
}
