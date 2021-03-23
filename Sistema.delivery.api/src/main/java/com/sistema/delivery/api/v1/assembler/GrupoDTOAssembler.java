package com.sistema.delivery.api.v1.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Grupo;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.GrupoController;
import com.sistema.delivery.api.v1.model.GrupoDTO;

@Component
public class GrupoDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public GrupoDTOAssembler() {
	      super(GrupoController.class, GrupoDTO.class);
	}
	
	@Override
	public GrupoDTO toModel(Grupo grupo)
	{
		GrupoDTO grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        
        if (sistemaSecurity.podeConsultarUsuariosGruposPermissoes()) {
        grupoModel.add(sistemaLink.linkToGrupos("grupos"));
        
        grupoModel.add(sistemaLink.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }
        
        return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoDTO> collectionModel = super.toCollectionModel(entities);
	    
	    if (sistemaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	        collectionModel.add(sistemaLink.linkToGrupos());
	    }
	    
	    return collectionModel;
	}   
	
}
