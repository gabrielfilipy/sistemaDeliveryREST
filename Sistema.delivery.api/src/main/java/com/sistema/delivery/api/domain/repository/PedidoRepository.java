package com.sistema.delivery.api.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema.delivery.api.domain.filtragem.VendasDiariaFiltra;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.domain.model.dto.VendaDiaria;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido>{

	//@Query("from Pedido where codigo = :codigo")
	Optional<Pedido> findByCodigo(String codigo);
	
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
	
	boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);
	
}
