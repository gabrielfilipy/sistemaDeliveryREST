package com.sistema.delivery.api.domain.exception;

public class FormaPagamentoNaoExisteExeption extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoExisteExeption(String mensagem) {
		super(mensagem);
	}

}
