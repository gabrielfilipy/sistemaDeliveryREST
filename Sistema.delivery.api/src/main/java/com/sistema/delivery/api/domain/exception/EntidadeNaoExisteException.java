package com.sistema.delivery.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoExisteException extends NegocioExeption{

	private static final long serialVersionUID = 1L;

	public EntidadeNaoExisteException(String mensagem)
	{
		super(mensagem);
	}
}
