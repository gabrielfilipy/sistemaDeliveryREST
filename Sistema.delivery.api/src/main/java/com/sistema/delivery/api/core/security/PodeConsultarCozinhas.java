package com.sistema.delivery.api.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("isAuthenticated()")//Antes de entrar nesse método verifique se essa expressao é verdadeira, se não lance uma exception de acesso negado
@Retention(RUNTIME)
@Target(METHOD)
public @interface PodeConsultarCozinhas {

}
