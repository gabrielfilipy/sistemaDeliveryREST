package com.sistema.delivery.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.sistema.delivery.api.domain.event.PedidoCanceladoEvent;
import com.sistema.delivery.api.domain.event.PedidoConfirmadoEvent;
import com.sistema.delivery.api.domain.exception.NegocioExeption;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido>{

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	
	private BigDecimal subtotal;
	
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	 
	@CreationTimestamp
	@Column(name = "data_criacao")
    private OffsetDateTime dataCriacao;

	@Column(name = "data_confirmacao")
    private OffsetDateTime dataConfirmacao;
	
	@Column(name = "data_cancelamento")
    private OffsetDateTime dataCancelamento;
	
	@Column(name = "data_entrega")
    private OffsetDateTime dataEntrega;
    
	
	@Embedded
	@Column(name = "endereco_entrega")
	private Endereco enderecoEntrega;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>(); 
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	//@Column(name = "forma_pagamento_id")
	private Pagamento formaPagamento;

	
	/**/
	public void calcularValorTotal()
	{
		getItens().forEach(ItemPedido::calcularPrecoTotal);
		
		this.subtotal = getItens().stream()
				.map(item -> item.getPrecoTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.valorTotal = this.subtotal.add(taxaFrete);
	}

	public void definirTaxaFrete()
	{
		setTaxaFrete(restaurante.getTaxaFrete());
	}

	public void atribuirPedidoAosItens()
	{
		getItens().forEach(item -> item.setPedido(this));
	}
	/**/
	
	public void confirmar()
	{
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		
		registerEvent(new PedidoConfirmadoEvent(this));
	}
	
	public void cancelar()
	{
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		
		registerEvent(new PedidoCanceladoEvent(this));
	}
	
	public void criar()
	{
		setStatus(StatusPedido.CRIADO);
		setDataCriacao(OffsetDateTime.now());
	}
	
	public void entrega()
	{
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}
	
	private void setStatus(StatusPedido novoStatus)
	{
		if(getStatus().naoPodeAlterarStatusPedidoPara(novoStatus))
		{
			throw new NegocioExeption(
					String.format("O Status do Pedido %s não pode ser alterado de %s para %s", 
							getCodigo(), getStatus(), novoStatus.getDescricao()));
		}
		
		this.status = novoStatus;
	}
	
	//Método de CALLBACK do JPA
	@PrePersist
	public void gerarCodigo()
	{
		setCodigo(UUID.randomUUID().toString());
	}
	
	public boolean podeSerConfirmado()
	{
		return getStatus().podeAlterarStatusPedidoPara(StatusPedido.CONFIRMADO);
	}
	
	public boolean podeSerEntregue()
	{
		return getStatus().podeAlterarStatusPedidoPara(StatusPedido.ENTREGUE);
	}
	
	public boolean podeSerCancelado()
	{
		return getStatus().podeAlterarStatusPedidoPara(StatusPedido.CANCELADO);
	}
	
}
