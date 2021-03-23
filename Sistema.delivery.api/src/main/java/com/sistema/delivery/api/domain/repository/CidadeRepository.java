package com.sistema.delivery.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.delivery.api.domain.model.Cidade;

public interface CidadeRepository extends CustomJpaRepository<Cidade, Long>{
 
}
