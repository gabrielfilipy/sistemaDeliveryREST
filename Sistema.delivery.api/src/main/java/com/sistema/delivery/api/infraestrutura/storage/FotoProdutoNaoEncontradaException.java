package com.sistema.delivery.api.infraestrutura.storage;

import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoExisteException{

	private static final long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
                produtoId, restauranteId));
    }
	
}
