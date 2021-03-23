package com.sistema.delivery.api.domain.repository;

import com.sistema.delivery.api.domain.model.FotoProduto;

public interface ProdutoRepositoryQuery {

	FotoProduto save(FotoProduto foto);
	void delete(FotoProduto foto);
	
}
