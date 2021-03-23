package com.sistema.delivery.api.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoExisteException{

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException(Long id)
	{
		this(String.format("A Cidade do ID: %d não existe", id));
	}
	
}
