package com.sistema.delivery.api.domain.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.annotation.HandlesTypes;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.delivery.api.core.storage.StorageProperties;
import com.sistema.delivery.api.core.validation.Groups;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "produto_id")
	private Long id;
	
	//Evitando SELECTs desnecessários
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	//A entidade Produto é mapeado através do Id da entidade fotoProduto
	private Produto produto;
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	
	public Long getRestauranteId() {
		if (getProduto() != null) {
			return getProduto().getRestaurante().getId();
		}
		
		return null;
	}
}
