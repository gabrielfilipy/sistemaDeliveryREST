package com.sistema.delivery.api.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValorZeroIncluiDescricaoValidador.class})
public @interface ValorZeroIncluiDescricao {

	String message() default "Descrição obrigatória";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
	
	String valorField();
	
	String descricaoField();
	
	String descricaoObrigatoriia();
	
}
