package com.sistema.delivery.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.exception.UsuarioNaoEncontradoException;
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.domain.repository.UsuarioRepository;
import com.sistema.delivery.api.domain.service.UsuarioRepositoryService;
import com.sistema.delivery.api.model.input.SenhaInput;
import com.sistema.delivery.api.model.input.UsuarioInputDTO;
import com.sistema.delivery.api.v1.assembler.UsuarioDTOAssembler;
import com.sistema.delivery.api.v1.assembler.UsuarioDTODisassembler;
import com.sistema.delivery.api.v1.model.UsuarioDTO;

@RestController
@RequestMapping(path = "/v1/usuario/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioRepositoryService usuarioRepositoryService;
	
	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@Autowired
	private UsuarioDTODisassembler usuarioDTODisassembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<UsuarioDTO> listar()
	{
		List<Usuario> todosUsuarios = usuarioRepository.findAll();
		return usuarioDTOAssembler.toCollectionModel(todosUsuarios);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("{id}")
	public UsuarioDTO retornaId(@PathVariable Long id)
	{
		Usuario usuarios = usuarioRepositoryService.existeOuNao(id);
		return usuarioDTOAssembler.toModel(usuarios);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	public UsuarioDTO adicionar(@RequestBody UsuarioInputDTO usuarioInputDTO)
	{
		Usuario usuario = usuarioDTODisassembler.toDomainObject(usuarioInputDTO);
		return usuarioDTOAssembler.toModel(usuarioRepositoryService.adicione(usuario));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("{id}")
	public void remover(@PathVariable Long id)
	{
		usuarioRepositoryService.remove(id);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody UsuarioInputDTO usuarioInputDTO)
	{
		Usuario usuarioAtual = usuarioRepositoryService.existeOuNao(id);
		usuarioDTODisassembler.copy(usuarioInputDTO, usuarioAtual);
		try {
			return usuarioDTOAssembler.toModel(usuarioRepositoryService.adicione(usuarioAtual));
		} catch (UsuarioNaoEncontradoException e) {
			throw new NegocioExeption(e.getMessage(), e);
		}
		
		catch (EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(e.getMessage());
		}
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@PutMapping("{idUsaurio}/senha")
	public void alterarSenha(@PathVariable Long idUsaurio, @RequestBody SenhaInput senha)
	{
		usuarioRepositoryService.alterarSenha(idUsaurio, senha.getSenhaAtual(), senha.getSenhaNova());
	}
	
}
