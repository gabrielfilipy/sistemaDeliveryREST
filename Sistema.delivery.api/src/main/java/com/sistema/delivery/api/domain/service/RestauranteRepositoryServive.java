package com.sistema.delivery.api.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.exception.RestauranteNaoExisteException;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.model.Pagamento;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.domain.repository.CozinhaRepository;
import com.sistema.delivery.api.domain.repository.RestauranteRepository;

@Service
public class RestauranteRepositoryServive {

	private static final String MSG_RESTAURANTE_CONFLITO = "O Restaurante do ID %d está em suo. Favor informe um Restaurante que não esteja em uso.";

	private static final String MSG_RESTAURANTE_NAO_EXISTE = "O Restaurante do ID %d não existe. Favor informe um Restaurante existente.";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepositoryService cozinhaRepositoryService;
	
	@Autowired
	private CidadeRepositoryService cidadeRepositoryService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private UsuarioRepositoryService usuarioRepositoryService;
	
	@Transactional
	public Restaurante adiciona(Restaurante restaurante)
	{
		Long id = restaurante.getCozinha().getId(); 
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cozinhaRepositoryService.buscarOuFalhar(id);
		Cidade cidade = cidadeRepositoryService.buscarOuFalhar(cidadeId);
		
		
		restaurante.setCozinha(cozinha); 
		restaurante.getEndereco().setCidade(cidade);
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void remova(Long id)
	{
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoExisteException(
					String.format(MSG_RESTAURANTE_NAO_EXISTE, id));
			 
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_CONFLITO, id));
		}
	} 
	
	/*Ativar Restaurante no singular*/
	@Transactional
	public void ativar(Long restauranteId)
	{
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.ativar();
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.fechar();
	}   
	
	/*Desativar Restaurante no singular*/
	@Transactional
	public void desativar(Long restauranteId)
	{
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.desativar();
	}
	
	/*Ativar Restaurante no plural*/
	@Transactional
	public void ativar(List<Long> restaurantes)
	{
		restaurantes.forEach(this::ativar);
	}
	
	/*Desativar Restaurante no plural*/
	@Transactional
	public void desativar(List<Long> restaurantes)
	{
		restaurantes.forEach(this::desativar);
	}
	
	@Transactional
	public void associarPagamento(Long restauranteId, Long formaPagamento)
	{
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Pagamento pagamento = formaPagamentoService.existeOuNao(formaPagamento);
		
		restaurante.associarFormaPagamento(pagamento);
	}
	
	@Transactional
	public void desassociarPagamento(Long restauranteId, Long formaPagamentoId)
	{
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Pagamento pagamento = formaPagamentoService.existeOuNao(formaPagamentoId);
		
		restaurante.desassociarFormaPagamento(pagamento);
	}
	
	@Transactional
	public void associarUsuario(Long restauranteId, Long usuarioId)
	{
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioRepositoryService.existeOuNao(usuarioId);
		restaurante.associarUsuario(usuario);
	}
	
	@Transactional
	public void desassociarUsuario(Long restauranteId, Long usuarioId)
	{
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioRepositoryService.existeOuNao(usuarioId);
		restaurante.desassociarUsuario(usuario);
	}
	
	public Restaurante buscarOuFalhar(Long id)
	{
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoExisteException(
					String.format(MSG_RESTAURANTE_NAO_EXISTE, id)));
	}

}
