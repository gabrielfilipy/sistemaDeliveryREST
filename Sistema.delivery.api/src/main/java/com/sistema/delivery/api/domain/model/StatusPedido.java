package com.sistema.delivery.api.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter 
public enum StatusPedido {  
 
	CRIADO("Criado"), 
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO), 
    CANCELADO("Cancelado", CRIADO);
	
    private String descricao;
	private List<StatusPedido> statusAnteriores;
	
	StatusPedido(String descricao, StatusPedido... statusAnteriores) {
		this.descricao = descricao; 
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao()
	{
		return this.descricao;
	}
    
	public Boolean naoPodeAlterarStatusPedidoPara(StatusPedido novoStatu)
	{
		return !novoStatu.statusAnteriores.contains(this);
	}
	
	public Boolean podeAlterarStatusPedidoPara(StatusPedido novoStatu)
	{
		return !naoPodeAlterarStatusPedidoPara(novoStatu);
	}
	
}
