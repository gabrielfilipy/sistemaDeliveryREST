package com.sistema.delivery.api.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException(Long id)
	{
		super(String.format("A Cozinha do ID: %d não existe", id));
	}

}
