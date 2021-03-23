package com.sistema.delivery.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
public class NegocioExeption extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NegocioExeption(String mensagem)
	{
		super(mensagem);
	} 
	
	public NegocioExeption(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
