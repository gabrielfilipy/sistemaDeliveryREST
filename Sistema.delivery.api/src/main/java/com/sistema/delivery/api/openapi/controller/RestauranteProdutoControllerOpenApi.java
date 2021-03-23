package com.sistema.delivery.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.model.input.ProdutoInputDTO;
import com.sistema.delivery.api.v1.model.ProdutoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	 @ApiOperation("Lista os produtos de um restaurante")
	    @ApiResponses({
	        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problema.class),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
	    })
	 CollectionModel<ProdutoDTO> listar(
	            @ApiParam(value = "ID do restaurante", example = "1", required = true)
	            Long restauranteId,
	            
	            @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", 
	                example = "false", defaultValue = "false")
	            Boolean incluirInativos);

	    @ApiOperation("Busca um produto de um restaurante")
	    @ApiResponses({
	        @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problema.class),
	        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problema.class)
	    })
	    ProdutoDTO buscar(
	            @ApiParam(value = "ID do restaurante", example = "1", required = true)
	            Long restauranteId,
	            
	            @ApiParam(value = "ID do produto", example = "1", required = true)
	            Long produtoId);

	    @ApiOperation("Cadastra um produto de um restaurante")
	    @ApiResponses({
	        @ApiResponse(code = 201, message = "Produto cadastrado"),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
	    })
	    ProdutoDTO adicionar(
	            @ApiParam(value = "ID do restaurante", example = "1", required = true)
	            Long restauranteId,
	            
	            @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true)
	            ProdutoInputDTO produtoInput);

	    @ApiOperation("Atualiza um produto de um restaurante")
	    @ApiResponses({
	        @ApiResponse(code = 200, message = "Produto atualizado"),
	        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problema.class)
	    })
	    ProdutoDTO atualizar(
	            @ApiParam(value = "ID do restaurante", example = "1", required = true)
	            Long restauranteId,
	            
	            @ApiParam(value = "ID do produto", example = "1", required = true)
	            Long produtoId,
	            
	            @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados", 
	                required = true)
	            ProdutoInputDTO produtoInput);
	
}
