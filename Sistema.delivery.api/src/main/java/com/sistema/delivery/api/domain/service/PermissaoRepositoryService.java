package com.sistema.delivery.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.delivery.api.domain.exception.PermissaoNaoExisteException;
import com.sistema.delivery.api.domain.model.Permissao;
import com.sistema.delivery.api.domain.repository.PermissaoRepository;

@Service
public class PermissaoRepositoryService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao existeOuNao(Long permissaoId)
	{
		return permissaoRepository.findById(permissaoId).orElseThrow(() -> 
		new PermissaoNaoExisteException(String.format("A permissão do ID %d não existe.", permissaoId)));
	}
	
}
