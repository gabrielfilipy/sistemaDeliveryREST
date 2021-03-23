package com.sistema.delivery.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputDTO {

	@ApiModelProperty(example = "Minas Gerais", required = true)
	private String nome;
	
}
