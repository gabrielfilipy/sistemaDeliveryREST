package com.sistema.delivery.api.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.sistema.delivery.api.domain.event.PedidoConfirmadoEvent;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.domain.service.EnvioEmailService;
import com.sistema.delivery.api.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmailService;
	
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event)
	{
		Pedido pedido = event.getPedido();
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				
				.build();
		//System.out.println("EMAIL DO CLIENTE = " + pedido.getCliente().getEmail());
		envioEmailService.enviar(mensagem);
	}
	
}
