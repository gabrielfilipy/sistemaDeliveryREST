package com.sistema.delivery.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.domain.service.UsuarioRepositoryService;
import com.sistema.delivery.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.assembler.GrupoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.UsuarioDTOAssembler;
import com.sistema.delivery.api.v1.model.GrupoDTO;

@RestController
@RequestMapping(path = "/v1/usuario/{usuarioId}/grupo/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi{

	@Autowired
	private UsuarioRepositoryService usuarioRepositoryService;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId)
	{
		Usuario usuario = usuarioRepositoryService.existeOuNao(usuarioId);
	    
	    CollectionModel<GrupoDTO> gruposModel = grupoDTOAssembler.toCollectionModel(usuario.getGrupos())
	            .removeLinks();
	    
	    if (sistemaSecurity.podeEditarUsuariosGruposPermissoes()) {
	        gruposModel.add(sistemaLink.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
	        
	        gruposModel.getContent().forEach(grupoModel -> {
	            grupoModel.add(sistemaLink.linkToUsuarioGrupoDesassociacao(
	                    usuarioId, grupoModel.getId(), "desassociar"));
	        });
	    }
	    
	    return gruposModel;
	}
	 
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId)
	{
		usuarioRepositoryService.associar(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId)
	{
		usuarioRepositoryService.desassociar(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
}
