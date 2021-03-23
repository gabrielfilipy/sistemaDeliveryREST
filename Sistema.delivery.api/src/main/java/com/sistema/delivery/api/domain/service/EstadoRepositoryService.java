package com.sistema.delivery.api.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.exception.EstadoNaoEncontradoException;
import com.sistema.delivery.api.domain.model.Estado;
import com.sistema.delivery.api.domain.repository.CozinhaRepository;
import com.sistema.delivery.api.domain.repository.EstadoRepository;

@Service
public class EstadoRepositoryService {

	private static final String MSG_ESTADO_EM_USO = 
			"O Estado do ID: %d está em uso, e não pode ser executado essa ação...";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Estado adiciona(Estado estado)
	{
		return estadoRepository.save(estado);
	}
	
	@Transactional
	public void remova(Long id)
	{
		try {
			estadoRepository.deleteById(id);
			estadoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));
		}
	}
	
	public Estado buscarOuFalhar(Long id)
	{
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}
	
}
