package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.model.input.CozinhaInputDTO;
import com.sistema.delivery.api.v1.model.CozinhaDTO;

@Component
public class CozinhaDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInputDTO cozinhaDTO)
	{
		return modelMapper.map(cozinhaDTO, Cozinha.class);
	}
	
	public void copy(CozinhaDTO cozinhaDTO, Cozinha cozinha)
	{
		modelMapper.map(cozinhaDTO, cozinha);
	}
}
