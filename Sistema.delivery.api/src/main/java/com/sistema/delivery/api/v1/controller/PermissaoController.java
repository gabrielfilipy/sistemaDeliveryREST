package com.sistema.delivery.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.model.Permissao;
import com.sistema.delivery.api.domain.repository.PermissaoRepository;
import com.sistema.delivery.api.openapi.controller.PermissaoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.PermissaoDTOAssembler;
import com.sistema.delivery.api.v1.model.PermissaoDTO;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi{

	  @Autowired
	  private PermissaoRepository permissaoRepository;
	    
	  @Autowired
	  private PermissaoDTOAssembler permissaoModelAssembler;
	    
	  @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	  @Override
	  @GetMapping
	  public CollectionModel<PermissaoDTO> listar() {
	      List<Permissao> todasPermissoes = permissaoRepository.findAll();
	        
	      return permissaoModelAssembler.toCollectionModel(todasPermissoes);
	  }  
	
}
