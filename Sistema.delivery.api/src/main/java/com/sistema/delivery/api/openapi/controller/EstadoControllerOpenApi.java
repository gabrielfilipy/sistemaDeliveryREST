package com.sistema.delivery.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.model.input.EstadoInputDTO;
import com.sistema.delivery.api.v1.model.EstadoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
    CollectionModel<EstadoDTO> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do estado inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problema.class)
    })
    EstadoDTO buscar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    EstadoDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
            EstadoInputDTO estadoInput);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Estado atualizado"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problema.class)
    })
    EstadoDTO atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId,
            
            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
            EstadoInputDTO estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Estado excluído"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problema.class)
    })
    void remover(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);
	
}
