package com.sistema.delivery.api.model.input;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput{

	@ApiModelProperty(example = "1", required = true)
	private Long id;
	
}
