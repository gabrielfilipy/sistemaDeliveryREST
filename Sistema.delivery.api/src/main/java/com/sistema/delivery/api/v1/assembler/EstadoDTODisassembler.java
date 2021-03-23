package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Estado;
import com.sistema.delivery.api.model.input.EstadoInputDTO;
import com.sistema.delivery.api.v1.model.EstadoDTO;

@Component
public class EstadoDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoInputDTO estadoInputDTO)
	{
		return modelMapper.map(estadoInputDTO, Estado.class);
	}
	
	public void copy(EstadoInputDTO estadoInputDTO, Estado estado)
	{
		modelMapper.map(estadoInputDTO, estado); 
	}
	
}
