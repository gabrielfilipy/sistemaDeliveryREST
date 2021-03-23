package com.sistema.delivery.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.model.input.CozinhaInputDTO;
import com.sistema.delivery.api.v1.model.CozinhaDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	 @ApiOperation("Lista as cozinhas com paginação")
	    PagedModel<CozinhaDTO> listar(Pageable pageable);
	    
	    @ApiOperation("Busca uma cozinha por ID")
	    @ApiResponses({
	        @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problema.class),
	        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
	    })
	    CozinhaDTO retornaId(
	    		@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
	            Long cozinhaId);
	    
	    @ApiOperation("Cadastra uma cozinha")
	    @ApiResponses({
	        @ApiResponse(code = 201, message = "Cozinha cadastrada"),
	    })
	    CozinhaDTO adicionar(
	    		@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
	            CozinhaInputDTO cozinhaInput);
	    
	    @ApiOperation("Atualiza uma cozinha por ID")
	    @ApiResponses({
	        @ApiResponse(code = 200, message = "Cozinha atualizada"),
	        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
	    })
	    CozinhaDTO atualizar(
	    		@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
	            Long cozinhaId,
	            
	            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
	            CozinhaInputDTO cozinhaInput);
	    
	    @ApiOperation("Exclui uma cozinha por ID")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Cozinha excluída"),
	        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
	    })
	    void remover(
	    		@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
	            Long cozinhaId);   
	
}
