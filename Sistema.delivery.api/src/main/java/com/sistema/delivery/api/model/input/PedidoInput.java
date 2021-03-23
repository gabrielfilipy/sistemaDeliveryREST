package com.sistema.delivery.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {


	@NotNull
	private RestauranteInputId restaurante;
	
	
	@NotNull
	private EnderecoInput enderecoEntrega;
	
	
	@NotNull
	private FormaPagamentoInputId formaPagamento;
	
	
	//@Size(min = 1)
	@NotNull
	private List<ItemPedidoInputDTO> itens;
	
}
