package com.sistema.delivery.api.model.mixin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.model.Endereco;
import com.sistema.delivery.api.domain.model.Pagamento;
import com.sistema.delivery.api.domain.model.Produto;

public class RestauranteMixin {
	
	//Ignorando a propriedade nome da cozinha. Somente em alterar um restaurante irá ignorar o nome da cozinha
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;

	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private List<Pagamento> pagamentos = new ArrayList<>();
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();
	
	//@JsonIgnore
	private OffsetDateTime data_cadastro;
	
	//@JsonIgnore
	private OffsetDateTime data_atualizacao;
	
	@JsonIgnore
	private Cidade cidade;
}
