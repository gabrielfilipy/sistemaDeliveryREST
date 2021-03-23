package com.sistema.delivery.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.CozinhaController;
import com.sistema.delivery.api.v1.model.CozinhaDTO;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public CozinhaDTOAssembler()
	{
		super(CozinhaController.class, CozinhaDTO.class);
	}
	
	@Override
	public CozinhaDTO toModel(Cozinha cozinha)
	{
		CozinhaDTO cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		    
		if (sistemaSecurity.podeConsultarCozinhas()) {
		  cozinhaModel.add(sistemaLink.linkToCozinhas("cozinhas"));
		}
		    
		return cozinhaModel;
	}
	 
}
