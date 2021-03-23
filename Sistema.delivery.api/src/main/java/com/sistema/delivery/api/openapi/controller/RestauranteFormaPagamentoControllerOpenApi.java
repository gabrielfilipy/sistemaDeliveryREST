package com.sistema.delivery.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.v1.model.FormaPagamentoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamentos")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
    })
    CollectionModel<FormaPagamentoDTO> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problema.class)
    })
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problema.class)
    })
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);
	
}
