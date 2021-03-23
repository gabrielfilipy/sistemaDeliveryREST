package com.sistema.delivery.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputDTOV2 {

	@ApiModelProperty(example = "Brasileira", required = true)
	@NotBlank
	private String nomeCozinha;
	
}
