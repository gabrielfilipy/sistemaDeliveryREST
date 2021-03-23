package com.sistema.delivery.api.v2.model;

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
public class CidadeDTOV2 extends RepresentationModel<CidadeDTOV2>{
 
	@ApiModelProperty(example = "1") 
	private Long idCidade;
	
	@ApiModelProperty(example = "Manaus")
	private String nomeCidade;
	
	private Long idEstado;
	private String nomeEstado;
	
}
