package com.sistema.delivery.api.infraestrutura.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sistema.delivery.api.domain.model.FotoProduto;
import com.sistema.delivery.api.domain.repository.ProdutoRepositoryQuery;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return manager.merge(foto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto foto) {
		manager.remove(foto);
	}
	
}
