package com.sistema.delivery.api.core.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*Cria construtor com parametro*/
@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;
	
}
