package com.sistema.delivery.api.v1.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Permissao;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.model.PermissaoDTO;

@Component
public class PermissaoDTOAssembler  implements RepresentationModelAssembler<Permissao, PermissaoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;   
	
	@Override
	public PermissaoDTO toModel(Permissao permissao) {
		PermissaoDTO permissaoModel = modelMapper.map(permissao, PermissaoDTO.class);
	    return permissaoModel;
	}
	
	public PermissaoDTO toDTO(Permissao permissao)
	{
		return modelMapper.map(permissao, PermissaoDTO.class);
	}
	
	@Override
    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
	CollectionModel<PermissaoDTO> collectionModel 
        = RepresentationModelAssembler.super.toCollectionModel(entities);

    if (sistemaSecurity.podeConsultarUsuariosGruposPermissoes()) {
        collectionModel.add(sistemaLink.linkToPermissoes());
    }
    
    return collectionModel; 
    } 
	
}
