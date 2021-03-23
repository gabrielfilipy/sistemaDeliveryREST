package com.sistema.delivery.api.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sistema.delivery.api.domain.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

	@Query("select max(dataAtualizacao) from Pagamento")
	OffsetDateTime getDataUltimaAtualizacao();
	
	@Query("select dataAtualizacao from Pagamento where id = :formaPagamentoId")
	OffsetDateTime getDataAtualizacaoById(Long formaPagamentoId); 
}
