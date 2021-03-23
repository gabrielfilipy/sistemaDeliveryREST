package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.model.input.PedidoInput;
import com.sistema.delivery.api.v1.model.PedidoDTO;

@Component
public class PedidoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInput pedidoInput)
	{
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	public void copy(PedidoInput pedidoInput, Pedido pedido)
	{
		modelMapper.map(pedidoInput, pedido);
	}
}
