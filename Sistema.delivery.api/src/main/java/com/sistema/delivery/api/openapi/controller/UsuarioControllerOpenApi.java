package com.sistema.delivery.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.model.input.SenhaInput;
import com.sistema.delivery.api.model.input.UsuarioInputDTO;
import com.sistema.delivery.api.model.input.UsuarioInputSenha;
import com.sistema.delivery.api.v1.model.UsuarioDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários")
	CollectionModel<UsuarioDTO> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problema.class)
    })
    UsuarioDTO buscar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
    UsuarioDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
            UsuarioInputSenha usuarioInput);
    
    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuário atualizado"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problema.class)
    })
    UsuarioDTO atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
                required = true)
            UsuarioInputDTO usuarioInput);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problema.class)
    })
    void alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de uma nova senha", 
                required = true)
            SenhaInput senha);
	
}
