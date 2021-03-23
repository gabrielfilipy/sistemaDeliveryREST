package com.sistema.delivery.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Estado;
import com.sistema.delivery.api.model.input.CidadeInputDTO;
import com.sistema.delivery.api.v2.model.input.CidadeInputDTOV2;

@Component
public class CidadeInputDisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;
	 
	public Cidade toDomainObject(CidadeInputDTOV2 cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTOV2 cidadeInput, Cidade cidade) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInput, cidade);
	}
	
}
