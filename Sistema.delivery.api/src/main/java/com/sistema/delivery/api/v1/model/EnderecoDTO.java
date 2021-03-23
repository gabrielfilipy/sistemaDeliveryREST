package com.sistema.delivery.api.v1.model;

import javax.persistence.Column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class EnderecoDTO {

	@ApiModelProperty(example = "38400-000")
	private String cep;
	
	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String logradouro;
	
	@ApiModelProperty(example = "\"1500\"")
	private String numero;
	
	@ApiModelProperty(example = "Apto 901")
	private String complemento;
	
	@ApiModelProperty(example = "Centro")
	private String bairro;
	
	
	private String rua;
	
	
	private CidadeResumoDTO cidade;
}
