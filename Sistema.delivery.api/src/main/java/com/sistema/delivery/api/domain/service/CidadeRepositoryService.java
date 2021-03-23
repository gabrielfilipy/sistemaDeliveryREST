package com.sistema.delivery.api.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.CidadeNaoEncontradaException;
import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Estado;
import com.sistema.delivery.api.domain.repository.CidadeRepository;
import com.sistema.delivery.api.domain.repository.EstadoRepository;

@Repository
public class CidadeRepositoryService {


	private static final String MSG_CIDADE_EM_USO = "A Cidade do ID: %d está em uso, e não pode ser executado essa ação...";
	private static final String MSG_CIDADE_NAO_EXISTE = "A Cidade do ID: %d não existe...";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepositoryService estadoService;
	
	@Transactional
	public Cidade adiciona(Cidade cidade)
	{
		
		Long id = cidade.getEstado().getId();
		System.out.println("Codigo do estado: " + id);
		Estado estado = estadoService.buscarOuFalhar(id);
		 
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade); 
	}
	
	@Transactional
	public void remova(Long id)
	{
		try {
			cidadeRepository.deleteById(id); 
			cidadeRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(
					String.format(MSG_CIDADE_NAO_EXISTE, id));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, id));
		}
	}
	
	public Cidade buscarOuFalhar(Long id)
	{
		return cidadeRepository.findById(id)
				.orElseThrow(() -> new CidadeNaoEncontradaException(
						String.format(MSG_CIDADE_NAO_EXISTE, id)));
	}
	
}
