package com.sistema.delivery.api.infraestrutura.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.sistema.delivery.api.domain.model.Restaurante;

public class RestauranteSpec {

	public static Specification<Restaurante> porFreteGratis()
	{
		return (root, query, builder) ->(builder.equal(root.get("taxaFrete"), BigDecimal.ZERO));
	}
	
	public static Specification<Restaurante> porNomeSemelhante(String nome)
	{
		return (root, query, builder) ->(builder.like(root.get("nome"), "%" + nome + "%"));
	}
	
}
