package com.sistema.delivery.api.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoExisteException{

	  private static final long serialVersionUID = 1L;
	    
	    public PedidoNaoEncontradoException(String codigo) {
	        super(String.format("Não existe um pedido com código %s", codigo));
	    } 
	
}
