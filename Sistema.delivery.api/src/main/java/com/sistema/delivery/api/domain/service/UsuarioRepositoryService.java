package com.sistema.delivery.api.domain.service;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.exception.UsuarioNaoEncontradoException;
import com.sistema.delivery.api.domain.model.Grupo;
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.domain.repository.UsuarioRepository;

@Service
public class UsuarioRepositoryService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepositoryService grupoRepositoryService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;  
	
	public Usuario adicione(Usuario usuario)
	{
		usuarioRepository.detach(usuario);
		Optional<Usuario> verificaUsuario = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(verificaUsuario.isPresent() && !verificaUsuario.equals(usuario.getEmail()))
		{
			throw new NegocioExeption(String.format("O Email %s já existe.", usuario.getEmail()));
		}
		
		if (usuario.isNovo()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	public void remove(Long id)
	{
		try {
			usuarioRepository.deleteById(id); 
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(id);
		}
		 
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("O usuário do ID %d está em uso.", id));
		}
	}
	
	/*
	 * Alterar senha do usuário do sistema
	 */
	@Transactional
	public void alterarSenha(Long idUsuario, String senhaAtual, String senhaNova)
	{
		Usuario usuario = existeOuNao(idUsuario);
		
		if(!passwordEncoder.matches(senhaAtual, usuario.getSenha()))
		{
			throw new NegocioExeption("Senha atual não é válida!");
		}
		
		usuario.setSenha(senhaNova);
	}
	
	@Transactional
	public void associar(Long usuarioId, Long grupoId)
	{
		Usuario usuario = existeOuNao(usuarioId);
		Grupo grupo = grupoRepositoryService.existeOuNao(grupoId);
		
		usuario.associar(grupo); 
	}
	
	@Transactional
	public void desassociar(Long usuarioId, Long grupoId)
	{
		Usuario usuario = existeOuNao(usuarioId);
		Grupo grupo = grupoRepositoryService.existeOuNao(grupoId);
		
		usuario.desassociar(grupo);
	}
	
	public Usuario existeOuNao(Long id)
	{
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
}
