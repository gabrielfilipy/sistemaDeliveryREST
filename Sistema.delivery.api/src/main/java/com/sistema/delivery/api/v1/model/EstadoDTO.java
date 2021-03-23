package com.sistema.delivery.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.sistema.delivery.api.model.input.EstadoIdInput;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Amazonas")
	private String nome;
	
}
