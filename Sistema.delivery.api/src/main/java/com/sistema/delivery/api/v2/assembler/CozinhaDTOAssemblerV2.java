package com.sistema.delivery.api.v2.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.CozinhaController;
import com.sistema.delivery.api.v1.model.CozinhaDTO;
import com.sistema.delivery.api.v2.controller.CozinhaControllerV2;
import com.sistema.delivery.api.v2.model.CozinhaDTOV2;

@Component
public class CozinhaDTOAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTOV2>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	public CozinhaDTOAssemblerV2()
	{
		super(CozinhaControllerV2.class, CozinhaDTOV2.class);
	}
	
	@Override
	public CozinhaDTOV2 toModel(Cozinha cozinha)
	{
		CozinhaDTOV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
	    modelMapper.map(cozinha, cozinhaModel);
	    
	    cozinhaModel.add(sistemaLink.linkToCozinhas("cozinhas"));
	    
	    return cozinhaModel;
	}
	 
}
