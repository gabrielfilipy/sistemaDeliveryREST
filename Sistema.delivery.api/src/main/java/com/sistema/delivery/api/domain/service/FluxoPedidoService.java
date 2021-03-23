package com.sistema.delivery.api.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.domain.model.StatusPedido;
import com.sistema.delivery.api.domain.repository.PedidoRepository;
import com.sistema.delivery.api.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmacao(String codigo)
	{
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
	}
	
	//TODO implementar Status de pedido de CONFIRMADO para ENTREGUE
	@Transactional
	public void entrega(String codigo)
	{
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
		pedido.entrega();
		
	}
	
	//TODO implementar Status de pedido de ENTREGUE para CANCELADO
	@Transactional
	public void cancelar(String codigo)
	{
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
	}
	
}
