package com.sistema.delivery.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.exception.GrupoNaoEncontradoException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Grupo;
import com.sistema.delivery.api.domain.repository.GrupoRepository;
import com.sistema.delivery.api.domain.service.GrupoRepositoryService;
import com.sistema.delivery.api.model.input.GrupoInputDTO;
import com.sistema.delivery.api.openapi.controller.GrupoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.GrupoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.GrupoDTODisassembler;
import com.sistema.delivery.api.v1.model.GrupoDTO;

@RestController
@RequestMapping(path = "/v1/grupo/", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi{

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoRepositoryService grupoRepositoryService;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoDTODisassembler grupoDTODisassembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar()
	{
		List<Grupo> todosGrupos = grupoRepository.findAll();
	    
	    return grupoDTOAssembler.toCollectionModel(todosGrupos);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("{id}")
	public GrupoDTO retornaId(@PathVariable Long id)
	{
		return grupoDTOAssembler.toModel(grupoRepositoryService.existeOuNao(id));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	public GrupoDTO adicionar(@RequestBody GrupoInputDTO grupoInputDTO)
	{
		Grupo grupo = grupoDTODisassembler.toDomainObject(grupoInputDTO);
		return grupoDTOAssembler.toModel(grupoRepositoryService.adiciona(grupo));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("{id}")
	public void remova(@PathVariable Long id)
	{	
		grupoRepositoryService.remova(id);
	} 
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("{id}")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody GrupoInputDTO grupoInputDTO)
	{
		Grupo grupoAtual = grupoRepositoryService.existeOuNao(id);
		grupoDTODisassembler.copy(grupoInputDTO, grupoAtual);
		try {
			
			return grupoDTOAssembler.toModel(grupoRepositoryService.adiciona(grupoAtual));
		} catch (GrupoNaoEncontradoException e) {
			throw new NegocioExeption(e.getMessage(), e);
		}
	}
	
}
