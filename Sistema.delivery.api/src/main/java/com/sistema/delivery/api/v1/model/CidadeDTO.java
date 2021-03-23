package com.sistema.delivery.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.sistema.delivery.api.model.input.EstadoIdInput;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO>{
 
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Manaus")
	private String nome;
	
	private EstadoDTO estado;
	
}
