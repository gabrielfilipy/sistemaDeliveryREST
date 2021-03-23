package com.sistema.delivery.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInputDTO {

	@ApiModelProperty(example = "Thai Gourmet", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "12.00", required = true)
	private BigDecimal taxaFrete;
	private CozinhaIdInput cozinha;
	
	@Valid
	@NotNull 
	private EnderecoInput endereco;
	
}
