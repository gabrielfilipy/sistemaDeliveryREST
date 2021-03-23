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
import com.sistema.delivery.api.domain.model.Grupo;
import com.sistema.delivery.api.domain.service.GrupoRepositoryService;
import com.sistema.delivery.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.assembler.PermissaoDTOAssembler;
import com.sistema.delivery.api.v1.model.PermissaoDTO;

@RestController
@RequestMapping(path = "/v1/grupo/{grupoId}/permissao/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi{

	@Autowired
	private GrupoRepositoryService grupoRepositoryService;
	
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity; 
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId)
	{
		 Grupo grupo = grupoRepositoryService.existeOuNao(grupoId);
		    
		    CollectionModel<PermissaoDTO> permissoesModel 
		        = permissaoDTOAssembler.toCollectionModel(grupo.getPermissoes())
		            .removeLinks(); 
		    
		    permissoesModel.add(sistemaLink.linkToGrupoPermissoes(grupoId));
		    
		    if (sistemaSecurity.podeEditarUsuariosGruposPermissoes()) {
		        permissoesModel.add(sistemaLink.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
		    
		        permissoesModel.getContent().forEach(permissaoModel -> {
		            permissaoModel.add(sistemaLink.linkToGrupoPermissaoDesassociacao(
		                    grupoId, permissaoModel.getId(), "desassociar"));
		        });
		    }
		    
		    return permissoesModel;
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId)
	{
		grupoRepositoryService.associar(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId)
	{
		grupoRepositoryService.desassociar(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
	
}
