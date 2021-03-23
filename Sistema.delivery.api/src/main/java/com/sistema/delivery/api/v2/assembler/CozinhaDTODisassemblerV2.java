package com.sistema.delivery.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.model.input.CozinhaInputDTO;
import com.sistema.delivery.api.v1.model.CozinhaDTO;
import com.sistema.delivery.api.v2.model.CozinhaDTOV2;
import com.sistema.delivery.api.v2.model.input.CozinhaInputDTOV2;

@Component
public class CozinhaDTODisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaDTOV2 cozinhaDTO)
	{
		return modelMapper.map(cozinhaDTO, Cozinha.class);
	}
	
	public void copy(CozinhaDTOV2 cozinhaDTO, Cozinha cozinha)
	{
		modelMapper.map(cozinhaDTO, cozinha);
	}
}
