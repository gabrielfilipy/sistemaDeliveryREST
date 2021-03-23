package com.sistema.delivery.api.openapi.model;

import java.math.BigDecimal;

import com.sistema.delivery.api.v1.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoModelOpenApi {

	/*
	 * Classe somente para fins de documentação
	 */
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "McDonalds")
	private String nome;
	
	@ApiModelProperty(example = "12,00")
	private BigDecimal taxaFrete;
	
	private CozinhaDTO cozinha;
	
}
