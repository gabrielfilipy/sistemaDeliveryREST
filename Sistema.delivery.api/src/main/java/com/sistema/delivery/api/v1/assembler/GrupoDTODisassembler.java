package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Grupo;
import com.sistema.delivery.api.model.input.GrupoInputDTO;
import com.sistema.delivery.api.v1.model.GrupoDTO;

@Component
public class GrupoDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInputDTO grupoDTO)
	{
		return modelMapper.map(grupoDTO, Grupo.class);
	}
	
	public void copy(GrupoInputDTO grupoDTO, Grupo grupo)
	{
		modelMapper.map(grupoDTO, grupo);
	}
	
}
