package com.sistema.delivery.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.CidadeController;
import com.sistema.delivery.api.v1.controller.FormaPagamentoController;
import com.sistema.delivery.api.v1.controller.PedidoController;
import com.sistema.delivery.api.v1.controller.RestauranteController;
import com.sistema.delivery.api.v1.controller.RestauranteProdutoController;
import com.sistema.delivery.api.v1.controller.UsuarioController;
import com.sistema.delivery.api.v1.model.PedidoDTO;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public PedidoDTOAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }
	
	@Override
	public PedidoDTO toModel(Pedido pedido)
	{
		PedidoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
	    modelMapper.map(pedido, pedidoModel);
	    
	    if (sistemaSecurity.podePesquisarPedidos()) {
	    pedidoModel.add(sistemaLink.linkToPedidos("pedidos"));
	    }
	    
	    
	    if(sistemaSecurity.podeGerenciarPedido(pedido.getCodigo())) {
		    if(pedido.podeSerConfirmado())
		    {
		    	pedidoModel.add(sistemaLink.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		    }
		    
		    if(pedido.podeSerCancelado())
		    {
		    	pedidoModel.add(sistemaLink.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		    }
		    
		    if(pedido.podeSerEntregue())
		    {
		    	pedidoModel.add(sistemaLink.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		    }
		}
	    
	    if (sistemaSecurity.podeConsultarRestaurantes()) {
	    pedidoModel.getRestaurante().add(
	    		sistemaLink.linkToRestaurante(pedido.getRestaurante().getId()));
	    }
	    
	    if (sistemaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	    pedidoModel.getCliente().add(
	    		sistemaLink.linkToUsuario(pedido.getCliente().getId()));
	    }
	    
	    if (sistemaSecurity.podeConsultarFormasPagamento()) {
	    pedidoModel.getFormaPagamento().add(
	    		sistemaLink.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
	    }
	    
	    if (sistemaSecurity.podeConsultarCidades()) {
	    pedidoModel.getEnderecoEntrega().getCidade().add(
	    		sistemaLink.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
	    }
	    
	 // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
	    if (sistemaSecurity.podeConsultarRestaurantes()) {
	    pedidoModel.getItens().forEach(item -> {
	        item.add(sistemaLink.linkToProduto(
	                pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
	    });
	    }
	        
	    return pedidoModel;
	}
	
}
