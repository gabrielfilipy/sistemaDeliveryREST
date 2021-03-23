package com.sistema.delivery.api.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.repository.PedidoRepository;
import com.sistema.delivery.api.domain.repository.RestauranteRepository;

@Component
public class SistemaSecurity {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Authentication getAuthentication() {
		//Pegando o contexto atual de segurança
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaUmRestaurante(Long restauranteId) {
		return restauranteRepository.existeResponsavel(restauranteId, getUsuarioId());
	}
	
	//Verificando se o restaurante é nulo ou não
	public boolean gerenciaRestaurante(Long restauranteId) {
	    if (restauranteId == null) {
	        return false;
	    }
	    
	    return restauranteRepository.existeResponsavel(restauranteId, getUsuarioId());
	} 
	
	//Método para validar o gerenciamento do pedido.
	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
	    return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
	}  
	
	//Só vai ser verdadeiro se os dois não forem nulos e os dois forem iguais.
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null
				 && getUsuarioId().equals(usuarioId); 
	}
	
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean podeGerenciarPedido(String codigoPedido) {
		return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS")
				 || gerenciaRestauranteDoPedido(codigoPedido));
	}
	
	public boolean isAutenticado() {
	    return getAuthentication().isAuthenticated();
	}
	
	//Verificando se o usuário possui os escopos de escrita e leitura
	public boolean temEscopoEscrita() {
	    return hasAuthority("SCOPE_WRITE");
	}

	public boolean temEscopoLeitura() {
	    return hasAuthority("SCOPE_READ");
	}
	
	//E agora outros métodos para identificarmos os escopos também
	
	public boolean podeConsultarRestaurantes() {
	    return temEscopoLeitura() && isAutenticado();
	}

	public boolean podeGerenciarCadastroRestaurantes() {
	    return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}

	public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
	    return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
	            || gerenciaRestaurante(restauranteId));
	}

	public boolean podeConsultarUsuariosGruposPermissoes() {
	    return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podeEditarUsuariosGruposPermissoes() {
	    return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
	    return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
	            || usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
	}

	public boolean podePesquisarPedidos() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarFormasPagamento() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarCidades() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarEstados() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarCozinhas() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarEstatisticas() {
	    return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
	}  
	
}
