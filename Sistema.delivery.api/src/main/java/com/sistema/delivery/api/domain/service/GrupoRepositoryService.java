package com.sistema.delivery.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.GrupoNaoEncontradoException;
import com.sistema.delivery.api.domain.model.Grupo;
import com.sistema.delivery.api.domain.model.Permissao;
import com.sistema.delivery.api.domain.repository.GrupoRepository;
import com.sistema.delivery.api.domain.repository.PermissaoRepository;

@Service
public class GrupoRepositoryService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoRepositoryService permissaoRepositoryService;
	
	public Grupo adiciona(Grupo grupo)
	{
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void remova(Long id)
	{
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
		
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O grupo do ID %d está em uso.", e));
		}
	}
	
	@Transactional
	public void associar(Long grupoId, Long permissaoId)
	{
		Grupo grupo = existeOuNao(grupoId);
		Permissao permissao = permissaoRepositoryService.existeOuNao(permissaoId);
		
		grupo.associar(permissao);
	}
	
	@Transactional
	public void desassociar(Long grupoId, Long permissaoId)
	{
		Grupo grupo = existeOuNao(grupoId);
		Permissao permissao = permissaoRepositoryService.existeOuNao(permissaoId);
		
		grupo.desassociar(permissao);
	}
	
	public Grupo existeOuNao(Long id)
	{
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(
				String.format("O grupo do ID %d não existe.", id)));
	}
	
}
