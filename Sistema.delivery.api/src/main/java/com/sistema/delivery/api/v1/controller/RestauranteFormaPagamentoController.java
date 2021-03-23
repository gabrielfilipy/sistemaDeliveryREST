package com.sistema.delivery.api.v1.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.core.validation.Groups;
import com.sistema.delivery.api.core.validation.ValidacaoException;
import com.sistema.delivery.api.domain.exception.CidadeNaoEncontradaException;
import com.sistema.delivery.api.domain.exception.CozinhaNaoEncontradaException;
import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.exception.RestauranteNaoExisteException;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.repository.RestauranteRepository;
import com.sistema.delivery.api.domain.service.RestauranteRepositoryServive;
import com.sistema.delivery.api.model.input.RestauranteInputDTO;
import com.sistema.delivery.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.assembler.FormaPagamentoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.RestauranteDTOAssembler;
import com.sistema.delivery.api.v1.assembler.RestauranteInputDisassembler;
import com.sistema.delivery.api.v1.model.CozinhaDTO;
import com.sistema.delivery.api.v1.model.FormaPagamentoDTO;
import com.sistema.delivery.api.v1.model.RestauranteDTO;

@RestController
@RequestMapping(path = "/v1/restaurante/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi{
	
	@Autowired
	private RestauranteRepositoryServive restauranteRepositoryService;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = restauranteRepositoryService.buscarOuFalhar(restauranteId);
	    
	    CollectionModel<FormaPagamentoDTO> formasPagamentoModel 
	        = formaPagamentoDTOAssembler.toCollectionModel(restaurante.getPagamentos())
	            .removeLinks();
	    
	    formasPagamentoModel.add(sistemaLink.linkToRestauranteFormasPagamento(restauranteId));

	    if (sistemaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
	        formasPagamentoModel.add(sistemaLink.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
	        
	        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
	            formaPagamentoModel.add(sistemaLink.linkToRestauranteFormaPagamentoDesassociacao(
	                    restauranteId, formaPagamentoModel.getId(), "desassociar"));
	        });
	    }
	    
	    return formasPagamentoModel;
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId)
	{
		restauranteRepositoryService.desassociarPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId)
	{
		restauranteRepositoryService.associarPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
}
