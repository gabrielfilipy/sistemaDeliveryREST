package com.sistema.delivery.api.model.input;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdInput {

	@ApiModelProperty(example = "1", required = true)
	private Long id;
	
}
