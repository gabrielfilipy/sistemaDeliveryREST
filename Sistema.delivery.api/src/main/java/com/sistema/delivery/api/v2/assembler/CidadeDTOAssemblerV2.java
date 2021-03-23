package com.sistema.delivery.api.v2.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.CidadeController;
import com.sistema.delivery.api.v1.controller.EstadoController;
import com.sistema.delivery.api.v1.model.CidadeDTO;
import com.sistema.delivery.api.v2.SistemaLinkV2;
import com.sistema.delivery.api.v2.controller.CidadeControllerV2;
import com.sistema.delivery.api.v2.model.CidadeDTOV2;

@Component
public class CidadeDTOAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDTOV2>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLinkV2 sistemaLink;
	
	public CidadeDTOAssemblerV2()
	{
		super(CidadeControllerV2.class, CidadeDTOV2.class);
	}
	
	@Override
	public CidadeDTOV2 toModel(Cidade cidade)
	{
		CidadeDTOV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
		    
		modelMapper.map(cidade, cidadeModel);
		    
		cidadeModel.add(sistemaLink.linkToCidades("cidades"));
		 
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeDTOV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		 return super.toCollectionModel(entities)
		            .add(sistemaLink.linkToCidades());
	}

}
