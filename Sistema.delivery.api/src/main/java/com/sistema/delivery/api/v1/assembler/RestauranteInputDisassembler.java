package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.model.input.RestauranteInputDTO;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO)
	{
		return modelMapper.map(restauranteInputDTO, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante)
	{
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null)
		{
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInputDTO, restaurante);
	}
	
}
