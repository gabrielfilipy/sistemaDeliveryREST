package com.sistema.delivery.api.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
 
	public EstadoNaoEncontradoException(Long id)
	{
		this(String.format("Estado do ID: %d não existe", id));
	}
} 
