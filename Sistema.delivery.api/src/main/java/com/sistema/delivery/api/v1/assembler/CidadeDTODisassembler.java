package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.model.input.CidadeInputDTO;

@Component
public class CidadeDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputDTO cidadeInputDTO)
	{
		return modelMapper.map(cidadeInputDTO, Cidade.class);
	}
	
	public void toCopy(CidadeInputDTO cidadeInputDTO, Cidade cidade)
	{
		modelMapper.map(cidadeInputDTO, cidade); 
	}
	
}
