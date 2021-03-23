package com.sistema.delivery.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.ProdutoNaoExisteException;
import com.sistema.delivery.api.domain.model.Produto;
import com.sistema.delivery.api.domain.repository.ProdutoRepository;

@Service
public class ProdutoRepositoryService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto adicione(Produto produto)
	{
		return produtoRepository.save(produto);
	}
	
	public Produto existeOuNao(Long produtoId, Long restauranteId)
	{
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoExisteException(restauranteId, produtoId));
	}
	
}
