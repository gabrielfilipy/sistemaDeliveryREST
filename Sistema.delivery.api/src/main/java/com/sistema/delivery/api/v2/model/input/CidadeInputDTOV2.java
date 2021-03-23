package com.sistema.delivery.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDTOV2 {

	@ApiModelProperty(example = "Manaus")
	private String nomeCidade;
	
	private Long idEstado;
}
