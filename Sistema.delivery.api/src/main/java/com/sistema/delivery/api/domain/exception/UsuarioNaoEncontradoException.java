package com.sistema.delivery.api.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Long id)
	{
		this(String.format("O Usuário do ID: %d não existe", id));
	}
}
