package com.sistema.delivery.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.sistema.delivery.api.domain.exception.CidadeNaoEncontradaException;
import com.sistema.delivery.api.domain.exception.EstadoNaoEncontradoException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.model.input.CidadeInputDTO;
import com.sistema.delivery.api.v1.model.CidadeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//Descrevendo tags na documentação e associando com controllers
@Api(tags = "Cidade")
public interface CidadeControllerOpenApi {

	//Descrevendo as operações de endpoints na documentação
		@ApiOperation("Listando todas as cidades")
		@ApiResponses({
			@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problema.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
		})
		CollectionModel<CidadeDTO> listar();
		
		@ApiOperation("Buscando uma cidade por seu identificador (ID)")
		//Descrevendo parâmetros de entrada na documentação
		CidadeDTO retornarId(
				@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
				Long id);
		
		@ApiOperation("Adicionando uma nova cidade")
		@ApiResponses({
			@ApiResponse(code = 400, message = "Cidade cadastrada")
		})
		CidadeDTO adiciona(
				@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) 
				CidadeInputDTO cidadeInputDTO);
		
		@ApiOperation("Removendo uma cidade")
		@ApiResponses({
			@ApiResponse(code = 204, message = "Cidade removida"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
		})
		void remova(
				@ApiParam(value = "ID de uma cidade", example = "1", required = true)
				Long id);
		
		@ApiOperation("Atualiza cidade")
		@ApiResponses({
			@ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
		})
		CidadeDTO alteraNaoParcial(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
				Long id, 
				@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") 
				CidadeInputDTO cidadeInputDTO);
	
}
