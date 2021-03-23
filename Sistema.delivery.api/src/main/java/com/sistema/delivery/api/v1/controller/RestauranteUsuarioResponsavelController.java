package com.sistema.delivery.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.service.RestauranteRepositoryServive;
import com.sistema.delivery.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.assembler.UsuarioDTOAssembler;
import com.sistema.delivery.api.v1.model.UsuarioDTO;

@RestController
@RequestMapping(path = "/v1/restaurante/{restauranteId}/usuario/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi{

	@Autowired
	private RestauranteRepositoryServive restauranteRepositoryService;
	
	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = restauranteRepositoryService.buscarOuFalhar(restauranteId);
	    
	    CollectionModel<UsuarioDTO> usuariosModel = usuarioDTOAssembler
	            .toCollectionModel(restaurante.getUsuarios())
	            .removeLinks();
	    
	    usuariosModel.add(sistemaLink.linkToRestauranteResponsaveis(restauranteId));
	    
	    if (sistemaSecurity.podeGerenciarCadastroRestaurantes()) {
	        usuariosModel.add(sistemaLink.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

	        usuariosModel.getContent().stream().forEach(usuarioModel -> {
	            usuarioModel.add(sistemaLink.linkToRestauranteResponsavelDesassociacao(
	                    restauranteId, usuarioModel.getId(), "desassociar"));
	        });
	    }
	    
	    return usuariosModel;
	}
	 
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("{usuarioId}") 
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId)
	{
		restauranteRepositoryService.associarUsuario(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	} 
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId)
	{
		restauranteRepositoryService.desassociarUsuario(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}
	
}
