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
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.CidadeController;
import com.sistema.delivery.api.v1.controller.EstadoController;
import com.sistema.delivery.api.v1.model.CidadeDTO;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public CidadeDTOAssembler()
	{
		super(CidadeController.class, CidadeDTO.class);
	}
	
	@Override
	public CidadeDTO toModel(Cidade cidade)
	{
		CidadeDTO cidadeModel = createModelWithId(cidade.getId(), cidade);
		    
		modelMapper.map(cidade, cidadeModel);
		    
		if (sistemaSecurity.podeConsultarCidades()) {
		cidadeModel.add(sistemaLink.linkToCidades("cidades"));
		}    
		
		if (sistemaSecurity.podeConsultarEstados()) {
		cidadeModel.getEstado().add(sistemaLink.linkToEstado(cidadeModel.getEstado().getId()));
		}    
		
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeDTO> collectionModel = super.toCollectionModel(entities);
	    
	    if (sistemaSecurity.podeConsultarCidades()) {
	        collectionModel.add(sistemaLink.linkToCidades());
	    }
	    
	    return collectionModel;
	}

}