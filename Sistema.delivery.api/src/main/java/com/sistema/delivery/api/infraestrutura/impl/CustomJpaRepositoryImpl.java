package com.sistema.delivery.api.infraestrutura.impl;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID>  extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager = entityManager;
	}

	@Override
	public Optional<T> listarPrimeiroNome() {
		var jpql = "from " + getDomainClass().getName();
		
		T entity = manager.createQuery(jpql, getDomainClass())
				.setFirstResult(1)
				.getSingleResult();
		return Optional.ofNullable(entity);
	}

	@Override
	public void detach(T entity) {
		manager.detach(entity);
	}

}
