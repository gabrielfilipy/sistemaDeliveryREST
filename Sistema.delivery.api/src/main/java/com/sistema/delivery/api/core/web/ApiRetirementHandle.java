package com.sistema.delivery.api.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ApiRetirementHandle extends HandlerInterceptorAdapter {

	/*
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object handler) throws Exception { //Pegando o
	 * caminho da requisição if(request.getRequestURI().startsWith("/v1/")) {
	 * response.addHeader("X-Sistema-Deprecated",
	 * "Essa API está depreciada e deixará de existir a partir de 01/05/2021" +
	 * "Use a versão mais atual"); } return true; }
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//Desabilitando uma versão da API
		//Pegando o caminho da requisição
		if(request.getRequestURI().startsWith("/v100/"))
		{
			response.setStatus(HttpStatus.GONE.value());
			return false;
		}
		return true;
	}
	
}
