package com.sistema.delivery.api.v2;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.v1.controller.CidadeController;
import com.sistema.delivery.api.v1.controller.CozinhaController;
import com.sistema.delivery.api.v1.controller.EstadoController;
import com.sistema.delivery.api.v1.controller.EstatisticasController;
import com.sistema.delivery.api.v1.controller.FluxoPedidoController;
import com.sistema.delivery.api.v1.controller.FormaPagamentoController;
import com.sistema.delivery.api.v1.controller.GrupoController;
import com.sistema.delivery.api.v1.controller.GrupoPermissaoController;
import com.sistema.delivery.api.v1.controller.PedidoController;
import com.sistema.delivery.api.v1.controller.PermissaoController;
import com.sistema.delivery.api.v1.controller.RestauranteController;
import com.sistema.delivery.api.v1.controller.RestauranteFormaPagamentoController;
import com.sistema.delivery.api.v1.controller.RestauranteProdutoController;
import com.sistema.delivery.api.v1.controller.RestauranteProdutoFotoController;
import com.sistema.delivery.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.sistema.delivery.api.v1.controller.UsuarioController;
import com.sistema.delivery.api.v1.controller.UsuarioGrupoController;
import com.sistema.delivery.api.v2.controller.CidadeControllerV2;
import com.sistema.delivery.api.v2.controller.CozinhaControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;

@Component
public class SistemaLinkV2 {

	public Link linkToCidades(String rel) { 
	    return WebMvcLinkBuilder.linkTo(CidadeControllerV2.class).withRel(rel);
	}

	public Link linkToCidades() {
	    return linkToCidades(IanaLinkRelations.SELF.value());
	} 
	
	public Link linkToCozinhas(String rel) {
	    return WebMvcLinkBuilder.linkTo(CozinhaControllerV2.class).withRel(rel);
	}

	public Link linkToCozinhas() {
	    return linkToCozinhas(IanaLinkRelations.SELF.value());
	}

}
