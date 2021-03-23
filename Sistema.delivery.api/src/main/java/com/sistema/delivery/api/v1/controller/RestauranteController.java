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
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.delivery.api.core.security.CheckSecurity;
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
import com.sistema.delivery.api.model.view.RestauranteView;
import com.sistema.delivery.api.openapi.controller.RestauranteControllerOpenApi;
import com.sistema.delivery.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.sistema.delivery.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.sistema.delivery.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.sistema.delivery.api.v1.assembler.RestauranteDTOAssembler;
import com.sistema.delivery.api.v1.assembler.RestauranteInputDisassembler;
import com.sistema.delivery.api.v1.model.CozinhaDTO;
import com.sistema.delivery.api.v1.model.RestauranteApenasNomeDTO;
import com.sistema.delivery.api.v1.model.RestauranteBasicoDTO;
import com.sistema.delivery.api.v1.model.RestauranteDTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
 
@RestController
@RequestMapping(path = "/v1/restaurante/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi{

	 /*
	 * Injeções de dependencias 
	 */
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteDTODisassembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	
	/*
	 * Classe de service onde está todas os metodos que altera dados
	 * Também todas as verificações cabiveis são feitas por lá*/
	@Autowired
	private RestauranteRepositoryServive restauranteService;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("por-nome-taxa-frete")
	public List<Restaurante> listarNomeFrete(
			String nome, 
			BigDecimal taxaFreteInicial, 
			BigDecimal taxaFreteFinal)
	{
		return restauranteRepository.listarNomeTaxaFrete(nome, 
				taxaFreteInicial,  
				taxaFreteFinal);
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("por-nome-semelhante-taxa-frete")
	public List<Restaurante> porNomeSemelhanteFreteGratis(
			String nome)
	{
		return restauranteRepository.porNomeSemelhanteFreteGratis(nome);
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<RestauranteBasicoDTO> listar()
	{
		return restauranteBasicoModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeDTO> listarApenasNomes()
	{
		return restauranteApenasNomeModelAssembler 
				.toCollectionModel(restauranteRepository.findAll()); 
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	/*Retorna restaurante por ID*/
	@GetMapping("{id}")
	public RestauranteDTO buscar(@PathVariable Long id)
	{
		Restaurante restaurante = restauranteService.buscarOuFalhar(id);
		return restauranteDTOAssembler.toModel(restaurante);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	/*Adiciona novo restaurante*/
	@PostMapping
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO)
	{
		try
		{ 
			/*Chamando o metodo que converte RESTAURANTE*/
			Restaurante restaurante = restauranteDTODisassembler.toDomainObject(restauranteInputDTO);
		return restauranteDTOAssembler.toModel(restauranteService.adiciona(restaurante));
		}catch(CozinhaNaoEncontradaException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	}
	
	/*Remove Restaurante pelo seu ID
	 *Já está programado para quando essa Entidade tiver relacionada em manutenções futuras
	 *para dar um tratamento de exceção e uma resposta para o consumidor da API*/
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("{id}")
	public void remova(@PathVariable Long id)
	{
		restauranteService.remova(id); 
	}
	
	/*Alterando de forma não parcial o registro*/
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("{id}")
	public RestauranteDTO atualizar(
			@PathVariable Long id, 
			@RequestBody @Valid RestauranteInputDTO restauranteInputDTO)
	{
		//Restaurante restaurante = restauranteDTODisassembler.toDomainObject(restauranteInputDTO);
		
		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
		
		restauranteDTODisassembler.copyToDomainObject(restauranteInputDTO, restauranteAtual);
		
//		BeanUtils.copyProperties(restaurante, restauranteAtual, 
//						"id", "taxa_frete", "cozinha_id", "endereco", "restaurante_forma_pagamento", 
//						"cidade_id", "restaurante");
		try
		{
		return restauranteDTOAssembler.toModel(restauranteService.adiciona(restauranteAtual));
		}catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	} 
	
	
//	@PatchMapping("{id}")
//	public RestauranteDTO alterarParcial(@PathVariable Long id, 
//			@RequestBody Map<String, Object> campos, HttpServletRequest request)
//	{
//		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
//		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
//	
//		try
//		{
//		
//		campos.forEach((nomePropriedade, valorPropriedade) ->{
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			
//			Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
//			
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//			field.setAccessible(true);
//			
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//			
//			ReflectionUtils.setField(field, restauranteAtual, novoValor);
//		});
//		
//		validar(restauranteAtual, "restaurante");
//		
//		}catch(IllegalArgumentException e)
//		{
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
//		}
//		
//		return alteraNaoParcial(id, restauranteAtual); 
//	}
	
	/*Ativa e desativando um Restaurante por vez*/
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("{restauranteId}/ativa")
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId)
	{
		restauranteService.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("{restauranteId}/desativa")
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId)
	{
		restauranteService.desativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	/*Ativando e desativando uma coleção de Restaurantes*/
	@PutMapping("ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantes)
	{
		restauranteService.ativar(restaurantes);
	}
	
	@DeleteMapping("ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantes)
	{
		restauranteService.desativar(restaurantes);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
		return ResponseEntity.noContent().build();
	}

}
