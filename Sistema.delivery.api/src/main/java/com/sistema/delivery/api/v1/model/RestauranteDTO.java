package com.sistema.delivery.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.delivery.api.model.view.RestauranteView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteDTO extends RepresentationModel<RestauranteDTO>{

	@ApiModelProperty(example = "1")
	//@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	//@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	//@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	//@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	
	private Boolean ativo;
	private Boolean aberto; 
	
	private EnderecoDTO endereco;
}
