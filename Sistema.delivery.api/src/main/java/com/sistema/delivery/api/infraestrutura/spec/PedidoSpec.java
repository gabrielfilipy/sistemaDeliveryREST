package com.sistema.delivery.api.infraestrutura.spec;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.sistema.delivery.api.domain.filtragem.PedidoFiltra;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.domain.model.Restaurante;

public class PedidoSpec {

	public static Specification<Pedido> usandoFiltro(PedidoFiltra pedidoCriteria)
	{
		return (root, query, builder) -> {
			
			if(Pedido.class.equals(query.getResultType()))
			{
			//TODO resolver problema de n+1
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("cliente");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if(pedidoCriteria.getClienteId() != null)
			{
				predicates.add(builder.equal(root.get("cliente"), pedidoCriteria.getClienteId()));
			}
			
			if(pedidoCriteria.getRestauranteId() != null)
			{
				predicates.add(builder.equal(root.get("restaurante"), pedidoCriteria.getRestauranteId()));
			}
			
			if(pedidoCriteria.getDataCriacaoInicio() != null)
			{
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), pedidoCriteria.getDataCriacaoInicio()));
			}
			
			if(pedidoCriteria.getDataCriacaoFim() != null)
			{
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), pedidoCriteria.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
