package com.sistema.delivery.api;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

//Anotação do Lombok. Tranforma uma classe que não pode ser extendida e adiciona um construtor. Ou seja, ela garante que essa classe será somente utilitaria
@UtilityClass
public class ResourceUriHelper {

	public static void addUriInResponseHeader(Object resourceId)
	{
		//ServletUriComponentsBuilder: Classe que ajuda criar URI usando as informações da requisição atual
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("{id}")
			.buildAndExpand(resourceId).toUri();
		
		//Representa a resposta
		HttpServletResponse response = ((ServletRequestAttributes) 
				RequestContextHolder.getRequestAttributes()).getResponse();
		response.setHeader(HttpHeaders.LOCATION, uri.toString());
	}
	
}
