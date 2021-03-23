package com.sistema.delivery.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInputDTO {
	
	@ApiModelProperty(example = "Cartão de crédito", required = true)
	private String descricao;
	
}
