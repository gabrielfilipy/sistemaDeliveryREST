package com.sistema.delivery.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.fasterxml.jackson.annotation.JsonView;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.model.view.RestauranteView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO>{

	@ApiModelProperty(example = "1")
	//@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@ApiModelProperty(example = "Brasileira")
	//@JsonView(RestauranteView.Resumo.class)
	private String nome; 
	
}
