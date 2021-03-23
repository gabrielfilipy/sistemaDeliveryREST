package com.sistema.delivery.api.infraestrutura.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.repository.RestauranteRepository;
import com.sistema.delivery.api.domain.repository.RestauranteRepositoryQuery;
import com.sistema.delivery.api.infraestrutura.spec.RestauranteSpec;

	/*
	 *Implementação do Repositorio de Restaurante 
	 */ 
@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery{

	@Autowired
	private EntityManager manager;
	
	@Autowired  @Lazy
	private RestauranteRepository restauranteRepository;

	/*Consulta dinamica usando JPQL customizado*/
	
	/*
	@Override
	public List<Restaurante> listarNomeTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		var jpql = new StringBuilder();
		jpql.append("from Restaurante where 0 = 0 ");
		
		//Nessa variavel pacote será armazenada as consultas
		var pacote = new HashMap<String, Object>();
		
		//Validando as variaveis que receberam os valores passados pelo consumidor da API
		if(StringUtils.hasText(nome))
		{
			jpql.append("and nome like :nome ");
			pacote.put("nome", "%" + nome + "%");
		}
		
		if(taxaFreteInicial != null)
		{
			jpql.append("and taxa_frete >= :taxaInicial ");
			pacote.put("taxaInicial", taxaFreteInicial);
		}
		
		if(taxaFreteFinal != null) 
		{
			jpql.append("and taxa_frete <= :taxaFinal ");
			pacote.put("taxaFinal", taxaFreteFinal);
		}
		 
		//Query tipada
		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
		
		//Percorrendo nosso pacote que possue as opções de consultas
		pacote.forEach((nomePropriedade, valorPropriedade) -> query.setParameter(nomePropriedade, valorPropriedade));
		
		//retornado uma query getResultList
		return query.getResultList();
	}
	*/
	
	/*Consulta dinamica usando Criteria API*/
	@Override
	public List<Restaurante> listarNomeTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteriaQuery = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteriaQuery.from(Restaurante.class);
		var predicados = new ArrayList<Predicate>(); 
		//Validando as variaveis que receberam os valores passados pelo consumidor da API
			if(StringUtils.hasText(nome))
			{
				predicados.add(builder.like(root.get("nome"), "%" + nome + "%"));
			}
				
			if(taxaFreteInicial != null)
			{
				predicados.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
			}
				
			if(taxaFreteFinal != null) 
			{
				predicados.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
			}
			criteriaQuery.where(predicados.toArray(new Predicate[0]));
			TypedQuery<Restaurante> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/*Consulta por nome semelhante e frete grátis utilizando o Specification*/
	@Override
	public List<Restaurante> porNomeSemelhanteFreteGratis(String nome) {
		RestauranteSpec r = new RestauranteSpec();
		return restauranteRepository.findAll(r.porFreteGratis().and(r.porNomeSemelhante(nome)));
	}
}
