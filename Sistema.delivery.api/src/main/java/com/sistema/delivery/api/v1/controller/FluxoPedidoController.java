package com.sistema.delivery.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.domain.repository.PedidoRepository;
import com.sistema.delivery.api.domain.service.EmissaoPedidoService;
import com.sistema.delivery.api.domain.service.FluxoPedidoService;
import com.sistema.delivery.api.model.input.PedidoInput;
import com.sistema.delivery.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.PedidoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.PedidoInputDisassembler;
import com.sistema.delivery.api.v1.assembler.PedidoResumoDTOAssembler;
import com.sistema.delivery.api.v1.model.PedidoDTO;
import com.sistema.delivery.api.v1.model.PedidoResumoDTO;

@RestController
@RequestMapping(path = "/v1/pedidos/{codigo}/", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi{

	@Autowired 
	private FluxoPedidoService fluxoPedidoService;
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar(@PathVariable String codigo)
	{
		fluxoPedidoService.confirmacao(codigo); 
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@PutMapping("entregue")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigo)
	{
		fluxoPedidoService.entrega(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@PutMapping("cancelado")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigo)
	{
		fluxoPedidoService.cancelar(codigo); 
		return ResponseEntity.noContent().build();
	}

}
