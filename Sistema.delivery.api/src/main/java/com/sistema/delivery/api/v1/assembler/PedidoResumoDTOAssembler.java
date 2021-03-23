package com.sistema.delivery.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.PedidoController;
import com.sistema.delivery.api.v1.controller.RestauranteController;
import com.sistema.delivery.api.v1.controller.UsuarioController;
import com.sistema.delivery.api.v1.model.PedidoDTO;
import com.sistema.delivery.api.v1.model.PedidoResumoDTO;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public PedidoResumoDTOAssembler()
	{
		super(PedidoController.class, PedidoResumoDTO.class);
	}
	
	@Override
	public PedidoResumoDTO toModel(Pedido pedido)
	{
		 PedidoResumoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		 modelMapper.map(pedido, pedidoModel);
		    
		 if (sistemaSecurity.podePesquisarPedidos()) {
		 pedidoModel.add(sistemaLink.linkToPedidos("pedidos"));
		 }   
		 
		 if (sistemaSecurity.podeConsultarRestaurantes()) {
		 pedidoModel.getRestaurante().add(
				 sistemaLink.linkToRestaurante(pedido.getRestaurante().getId()));
		 }
		 
		 if (sistemaSecurity.podeConsultarUsuariosGruposPermissoes()) {
		 pedidoModel.getNomeCliente().add(sistemaLink.linkToUsuario(pedido.getCliente().getId()));
		 }   
		 
	     return pedidoModel;
	}

}
