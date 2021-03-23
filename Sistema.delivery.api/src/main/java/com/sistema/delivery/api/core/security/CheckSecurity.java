package com.sistema.delivery.api.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

	/*
	 * @PreAuthorize antes da execução do método que está anotado com algumas dessas
	 * anotações abaixo criado; checa a expressão colocada na mesma.
	 * 
	 * Já o @PostAuthorize ele deixa o método ser executado primeiro para depois ele checa
	 */

public @interface CheckSecurity {

	//Anotações que vão servir para restringir acesso
	
	public @interface Cozinhas{
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
		
		@PreAuthorize("@sistemaSecurity.podeConsultarCozinhas()")//Antes de entrar nesse método verifique se essa expressao é verdadeira, se não lance uma exception de acesso negado
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
	}
	
	public @interface Restaurantes {

	    @PreAuthorize("@sistemaSecurity.podeGerenciarCadastroRestaurantes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarCadastro { }

	    @PreAuthorize("@sistemaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarFuncionamento { }
	    
	    @PreAuthorize("@sistemaSecurity.podeConsultarRestaurantes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Pedidos {

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
				+ "@sistemaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or "
				+ "@sistemaSecurity.gerenciaUmRestaurante(returnObject.restaurante.id)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeBuscar { }
		
		@PreAuthorize("@sistemaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodePesquisar { }
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeCriar { }

		//TESTAR BUSCA DE PEDIDOS
		
		@PreAuthorize("@sistemaSecurity.podeGerenciarPedido(#codigoPedido)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarPedidos {
		}
	    
	}
	
	public @interface FormasPagamento {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
	    @Retention(RUNTIME)
	    @Target(METHOD) 
	    public @interface PodeEditar { }

	    @PreAuthorize("@sistemaSecurity.podeConsultarFormasPagamento()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Cidades {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("@sistemaSecurity.podeConsultarCidades()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}

	public @interface Estados {
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("@sistemaSecurity.podeConsultarEstados()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface UsuariosGruposPermissoes {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
	            + "@sistemaSecurity.usuarioAutenticadoIgual(#usuarioId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarPropriaSenha { }
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
	            + "@sistemaSecurity.usuarioAutenticadoIgual(#usuarioId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarUsuario { }

	    @PreAuthorize("@sistemaSecurity.podeEditarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }
	    

	    @PreAuthorize("@sistemaSecurity.podeConsultarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Estatisticas {

	    @PreAuthorize("@sistemaSecurity.podeConsultarEstatisticas()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
}
