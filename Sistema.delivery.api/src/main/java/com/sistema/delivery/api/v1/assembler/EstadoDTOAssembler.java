package com.sistema.delivery.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Estado;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.CidadeController;
import com.sistema.delivery.api.v1.controller.EstadoController;
import com.sistema.delivery.api.v1.model.CidadeDTO;
import com.sistema.delivery.api.v1.model.EstadoDTO;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public EstadoDTOAssembler()
	{
		super(EstadoController.class, EstadoDTO.class);
	}
	
	@Override
	public EstadoDTO toModel(Estado estado)
	{
		EstadoDTO estadoModel = createModelWithId(estado.getId(), estado);
	    modelMapper.map(estado, estadoModel);
	    
	    if (sistemaSecurity.podeConsultarEstados()) {
	    estadoModel.add(sistemaLink.linkToEstados("estados"));
	    }
	    
	    return estadoModel;

	}
	
	@Override
	public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
		CollectionModel<EstadoDTO> collectionModel = super.toCollectionModel(entities);
	    
	    if (sistemaSecurity.podeConsultarEstados()) {
	        collectionModel.add(sistemaLink.linkToEstados());
	    }
	    
	    return collectionModel;
	}
	
}
