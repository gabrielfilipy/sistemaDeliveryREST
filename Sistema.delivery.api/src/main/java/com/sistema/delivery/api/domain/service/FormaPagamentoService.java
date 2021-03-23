package com.sistema.delivery.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.FormaPagamentoNaoExisteExeption;
import com.sistema.delivery.api.domain.model.Pagamento;
import com.sistema.delivery.api.domain.repository.PagamentoRepository;

@Service
public class FormaPagamentoService {

	private static final String MSG_NAO_EXISTE = "Essa forma de pagamento do ID %d não existe";
	@Autowired
	private PagamentoRepository formaPagamento;
	
	@Transactional
	public Pagamento adiciona(Pagamento pagamento)
	{
		return formaPagamento.save(pagamento);
	}
	
	public void remova(Long id)
	{
		try
		{
			formaPagamento.deleteById(id);
		}catch(EmptyResultDataAccessException e)
		{
			throw new FormaPagamentoNaoExisteExeption(String.format(MSG_NAO_EXISTE, id));
		}catch(DataAccessException e)
		{
			throw new EntidadeEmUsoException(e.getMessage());
		}
	}
	
	public Pagamento existeOuNao(Long id)
	{
		return formaPagamento.findById(id).orElseThrow(() -> new FormaPagamentoNaoExisteExeption(
				String.format(MSG_NAO_EXISTE, id)));
	}
	
}
