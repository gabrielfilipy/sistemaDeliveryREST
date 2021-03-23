package com.sistema.delivery.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	private String nome; 
	
	
	@Column(name = "taxa_frete") 
	private BigDecimal taxaFrete;
	
	//Ignorando a propriedade nome da cozinha. Somente em alterar um restaurante irá ignorar o nome da cozinha
	@ManyToOne
	@JoinColumn(name = "cozinha_id")
	private Cozinha cozinha;

	@Embedded
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE; 
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
	joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "pagamento_id"))
	private Set<Pagamento> pagamentos = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurante")
	/*Um 'Set<exemplo> é um conjunto, e um conjunto não aceita elementos duplicados'*/
	private List<Produto> produtos = new ArrayList<>();
	 
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime data_cadastro;
	
	@UpdateTimestamp  
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime data_atualizacao;
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", 
	joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> usuarios = new HashSet<>();
	
	public void associarFormaPagamento(Pagamento pagamento)
	{
		getPagamentos().add(pagamento);
	}
	
	public void desassociarFormaPagamento(Pagamento pagamento)
	{
		getPagamentos().remove(pagamento);
	}
	
	public void associarUsuario(Usuario usuario)
	{
		getUsuarios().add(usuario);
	}
	
	public void desassociarUsuario(Usuario usuario)
	{
		getUsuarios().remove(usuario); 
	}
	
	public void ativar()
	{
		setAtivo(true);
	}
	
	public void desativar()
	{
		setAtivo(false);
	}
	
	public void abrir() {
	    setAberto(true);
	}

	public void fechar() {
	    setAberto(false);
	}   
	
	public Boolean aceitaFormaPagamento(Pagamento formaPagamento)
	{
		return getPagamentos().contains(formaPagamento);
	}
	
	public Boolean naoAceitaFormaPagamento(Pagamento formaPagamento)
	{
		return !aceitaFormaPagamento(formaPagamento);
	}
	
	public boolean isAberto() {
	    return this.aberto;
	}

	public boolean isFechado() {
	    return !isAberto();
	}

	public boolean isInativo() {
	    return !isAtivo();
	}

	public boolean isAtivo() {
	    return this.ativo;
	}

	public boolean aberturaPermitida() {
	    return isAtivo() && isFechado();
	}

	public boolean ativacaoPermitida() {
	    return isInativo();
	}

	public boolean inativacaoPermitida() {
	    return isAtivo();
	}

	public boolean fechamentoPermitido() {
	    return isAberto();
	}  
	
}

