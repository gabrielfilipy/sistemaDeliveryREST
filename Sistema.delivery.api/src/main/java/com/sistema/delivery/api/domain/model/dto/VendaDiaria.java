package com.sistema.delivery.api.domain.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Cria um construtor com esses três argumentos
@AllArgsConstructor
@Getter
@Setter
public class VendaDiaria {

	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
	
}
