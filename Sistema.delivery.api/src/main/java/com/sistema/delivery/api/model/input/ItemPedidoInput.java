package com.sistema.delivery.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@NotNull
	private Long id;
	
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	private String observacao;
	
}
