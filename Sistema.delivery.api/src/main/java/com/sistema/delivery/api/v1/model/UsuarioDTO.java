package com.sistema.delivery.api.v1.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "João da Silva")
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "joao.ger@algafood.com.br")
	@NotBlank
	@Email
	private String email;
	
}
