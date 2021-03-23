package com.sistema.delivery.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Builder
public class Problema {
	
	@ApiModelProperty(example = "400")
	private Integer status;
	
	@ApiModelProperty(example = "hhtp://monetsystem.com.br/dados-invalidos")
	private String type; 
	
	@ApiModelProperty(example = "Dados inválidos")
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
	private String userMessage;
	
	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z")
	private OffsetDateTime timeStamp;
	
	@ApiModelProperty("Objetos ou campos que geraram o erro (Opcional)")
	private List<Object> fields;
	
	@ApiModel("ObjetoProblema")
	@Getter
	@Setter
	@Builder 
	public static class Object
	{
		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
		
		@ApiModelProperty(example = "preco")
		private String name;
	}
}
