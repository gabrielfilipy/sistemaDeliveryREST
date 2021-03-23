package com.sistema.delivery.api.domain.exception;

public class ProdutoNaoExisteException extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

	public ProdutoNaoExisteException(String mensagem) {
		super(mensagem);
	}
	
	 public ProdutoNaoExisteException(Long restauranteId, Long produtoId) {
	        this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d", 
	                produtoId, restauranteId));
	}

}
