package com.sistema.delivery.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.model.input.GrupoInputDTO;
import com.sistema.delivery.api.v1.model.GrupoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	CollectionModel<GrupoDTO> listar();
    
    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problema.class)
    })
    GrupoDTO retornaId(
    		@ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);
    
    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GrupoDTO adicionar(
    		@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
            GrupoInputDTO grupoInput);
    
    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Grupo atualizado"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problema.class)
    })
    GrupoDTO atualizar(
    		@ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId,
            
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", 
    		required = true)
            GrupoInputDTO grupoInput);
    
    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Grupo excluído"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problema.class)
    })
    void remova(
    		@ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);
	
}
