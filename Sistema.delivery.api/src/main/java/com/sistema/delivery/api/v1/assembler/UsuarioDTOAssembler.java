package com.sistema.delivery.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
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
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.CidadeController;
import com.sistema.delivery.api.v1.controller.EstadoController;
import com.sistema.delivery.api.v1.controller.UsuarioController;
import com.sistema.delivery.api.v1.model.CidadeDTO;
import com.sistema.delivery.api.v1.model.UsuarioDTO;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public UsuarioDTOAssembler()
	{
		super(UsuarioController.class, UsuarioDTO.class);
	}
	
	@Override
	public UsuarioDTO toModel(Usuario usuario)
	{
		UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
		modelMapper.map(usuario, usuarioDTO);
		    
		if (sistemaSecurity.podeConsultarUsuariosGruposPermissoes()) {
		usuarioDTO.add(sistemaLink.linkToUsuarios("usuarios"));
		    
		usuarioDTO.add(sistemaLink.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
		}
		
		return usuarioDTO;
	}
	
	@Override
	public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
		        .add(sistemaLink.linkToUsuarios());
	}
	
	
}
