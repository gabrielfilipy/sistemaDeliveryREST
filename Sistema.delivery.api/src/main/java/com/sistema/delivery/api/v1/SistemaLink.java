package com.sistema.delivery.api.v1;

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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;

@Component
public class SistemaLink {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM)
		);
	
	public Link linkToPedidos(String rel)
	{
    TemplateVariables filtroVariables = new TemplateVariables(
    		new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
    		new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
    		new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
    		new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM)
    		);
    String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
   
    return new Link(UriTemplate.of(pedidosUrl, 
    		PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel)
	{
		return WebMvcLinkBuilder.linkTo(methodOn(FluxoPedidoController.class)
				.confirmar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToEntregaPedido(String codigoPedido, String rel)
	{
		return WebMvcLinkBuilder.linkTo(methodOn(FluxoPedidoController.class)
				.entregar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel)
	{
		return WebMvcLinkBuilder.linkTo(methodOn(FluxoPedidoController.class)
				.cancelar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteController.class)
	            .buscar(restauranteId)).withRel(rel);
	}

	public Link linkToRestaurante(Long restauranteId) {
	    return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuario(Long usuarioId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(UsuarioController.class)
	            .retornaId(usuarioId)).withRel(rel);
	}

	public Link linkToUsuario(Long usuarioId) {
	    return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuarios(String rel) {
	    return WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel(rel);
	}

	public Link linkToUsuarios() {
	    return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public Link linkToGruposUsuario(Long usuarioId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(UsuarioGrupoController.class)
	            .listar(usuarioId)).withRel(rel);
	}

	public Link linkToGruposUsuario(Long usuarioId) {
	    return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
	            .listar(restauranteId)).withRel(rel);
	}

	public Link linkToResponsaveisRestaurante(Long restauranteId) {
	    return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(FormaPagamentoController.class)
	            .buscar(formaPagamentoId, null)).withRel(rel);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
	    return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidade(Long cidadeId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(CidadeController.class)
	            .retornarId(cidadeId)).withRel(rel);
	}

	public Link linkToCidade(Long cidadeId) {
	    return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String rel) {
	    return WebMvcLinkBuilder.linkTo(CidadeController.class).withRel(rel);
	}

	public Link linkToCidades() {
	    return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToEstado(Long estadoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(EstadoController.class)
	            .buscar(estadoId)).withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
	    return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstados(String rel) {
	    return WebMvcLinkBuilder.linkTo(EstadoController.class).withRel(rel);
	}

	public Link linkToEstados() {
	    return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteProdutoController.class)
	            .buscar(restauranteId, produtoId))
	            .withRel(rel);
	}

	public Link linkToProduto(Long restauranteId, Long produtoId) {
	    return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinhas(String rel) {
	    return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
	}

	public Link linkToCozinhas() {
	    return linkToCozinhas(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantes(String rel) {
		String restaurantesUrl = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();
	    
	    return new Link(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
	}

	public Link linkToRestaurantes() {
	    return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteFormaPagamentoController.class)
	            .listar(restauranteId)).withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(CozinhaController.class)
	            .retornaId(cozinhaId)).withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId) {
	    return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	} 
	
	public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.listar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteResponsaveis(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteController.class)
	            .abrir(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteController.class)
	            .fechar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteController.class)
	            .inativar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteController.class)
	            .ativar(restauranteId)).withRel(rel);
	}
	
	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
	
	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
	    return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFormasPagamento(String rel) {
	    return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel(rel);
	}

	public Link linkToFormasPagamento() {
	    return linkToFormasPagamento(IanaLinkRelations.SELF.value());
	}  
	
	/*
	 * Associações de formas de pagamentos
	 */
	public Link linkToRestauranteFormaPagamentoDesassociacao(
			Long restauranteId, Long formaPagamentoId, String rel)
	{
		return WebMvcLinkBuilder.linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.desassociar(restauranteId, formaPagamentoId)).withRel(rel);
	}
	
	public Link linkToRestauranteFormaPagamentoAssociacao(
			Long restauranteId, String rel)
	{
		return WebMvcLinkBuilder.linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.associar(restauranteId, null)).withRel(rel);
	} 
	
	public Link linkToRestauranteResponsavelDesassociacao(
			Long restauranteId, Long usuarioId, String rel) {

		   return WebMvcLinkBuilder.linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
		            .desassociar(restauranteId, usuarioId)).withRel(rel);
	}

	public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel) {
		   return WebMvcLinkBuilder.linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
		            .associar(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToProdutos(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteProdutoController.class)
	            .listar(restauranteId, null)).withRel(rel);
	}
 
	public Link linkToProdutos(Long restauranteId) {
	    return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(RestauranteProdutoFotoController.class)
	            .buscar(restauranteId, produtoId)).withRel(rel);
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
	    return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGrupos(String rel) {
	    return WebMvcLinkBuilder.linkTo(GrupoController.class).withRel(rel);
	}

	public Link linkToGrupos() {
	    return linkToGrupos(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(GrupoPermissaoController.class)
	            .listar(grupoId)).withRel(rel);
	} 
	
	public Link linkToPermissoes(String rel) {
	    return WebMvcLinkBuilder.linkTo(PermissaoController.class).withRel(rel);
	}

	public Link linkToPermissoes() {
	    return linkToPermissoes(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId) {
	    return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(GrupoPermissaoController.class)
	            .associar(grupoId, null)).withRel(rel);
	}

	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(GrupoPermissaoController.class)
	            .desassociar(grupoId, permissaoId)).withRel(rel);
	}
	
	public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(UsuarioGrupoController.class)
	            .associar(usuarioId, null)).withRel(rel);
	}

	public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(methodOn(UsuarioGrupoController.class)
	            .desassociar(usuarioId, grupoId)).withRel(rel);
	}  
	
	public Link linkToEstatisticas(String rel) {
	    return WebMvcLinkBuilder.linkTo(EstatisticasController.class).withRel(rel);
	}

	public Link linkToEstatisticasVendasDiarias(String rel) {
	    TemplateVariables filtroVariables = new TemplateVariables(
	            new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
	            new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
	    
	    String pedidosUrl = WebMvcLinkBuilder.linkTo(methodOn(EstatisticasController.class)
	            .consultarVendasDiarias(null, null)).toUri().toString();
	    
	    return new Link(UriTemplate.of(pedidosUrl, filtroVariables), rel);
	} 
}
