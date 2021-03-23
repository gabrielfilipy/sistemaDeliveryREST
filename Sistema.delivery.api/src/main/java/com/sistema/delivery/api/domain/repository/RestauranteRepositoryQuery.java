package com.sistema.delivery.api.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.sistema.delivery.api.domain.model.Restaurante;

public interface RestauranteRepositoryQuery {

	public List<Restaurante> listarNomeTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	public List<Restaurante> porNomeSemelhanteFreteGratis(String nome);
	
}