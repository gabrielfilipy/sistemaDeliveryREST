package com.sistema.delivery.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RestauranteNaoExisteException extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

	public RestauranteNaoExisteException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoExisteException(Long id)
	{
		super(String.format("O Restaurante do ID: %d não existe", id)); 
	}

}
