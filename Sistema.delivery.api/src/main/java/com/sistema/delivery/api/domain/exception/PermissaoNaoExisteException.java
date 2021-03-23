package com.sistema.delivery.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PermissaoNaoExisteException extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

	public PermissaoNaoExisteException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoNaoExisteException(Long id)
	{
		super(String.format("A permissão do ID: %d não existe", id)); 
	}

}
