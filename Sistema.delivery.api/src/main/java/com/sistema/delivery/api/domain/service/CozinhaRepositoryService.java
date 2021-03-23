package com.sistema.delivery.api.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.CozinhaNaoEncontradaException;
import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.repository.CozinhaRepository;

@Service
public class CozinhaRepositoryService {

	private static final String MSG_COZINHA_EM_USO = 
			"A Cozinha do ID: %d está em uso, e não pode ser executado essa ação...";
	
	private static final String MSG_COZINHA_NAO_EXISTE =
			"A Cozinha do ID: %d não existe...";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha adiciona(Cozinha cozinha)
	{
		return cozinhaRepository.save(cozinha); 
	}
	
	@Transactional
	public void remova(Long id)
	{
		try {
			cozinhaRepository.deleteById(id); 
			cozinhaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(
					String.format(MSG_COZINHA_NAO_EXISTE, id));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, id));
		}
	} 
	
	public Cozinha buscarOuFalhar(Long id) 
	{
		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(
						String.format(MSG_COZINHA_NAO_EXISTE, id)));
	}
	
}
