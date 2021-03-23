package com.sistema.delivery.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDTO {

	@ApiModelProperty(example = "Manaus")
	private String nome;
	
	
	private EstadoIdInput estado;
	
}
