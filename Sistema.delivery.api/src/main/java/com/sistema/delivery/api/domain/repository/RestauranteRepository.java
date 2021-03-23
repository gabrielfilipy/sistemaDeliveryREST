package com.sistema.delivery.api.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema.delivery.api.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQuery, 
JpaSpecificationExecutor<Restaurante>{

	@Query("from Restaurante where id = :id")
	public Restaurante retornaid(Long id);
	
	boolean existeResponsavel(Long restauranteId, Long usuarioId);
}
