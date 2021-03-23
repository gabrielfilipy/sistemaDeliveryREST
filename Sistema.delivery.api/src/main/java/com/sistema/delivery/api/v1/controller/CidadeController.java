package com.sistema.delivery.api.v1.controller;

import java.lang.reflect.Field;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.delivery.api.ResourceUriHelper;
import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.exception.CidadeNaoEncontradaException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.exception.EstadoNaoEncontradoException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.repository.CidadeRepository;
import com.sistema.delivery.api.domain.service.CidadeRepositoryService;
import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.model.input.CidadeInputDTO;
import com.sistema.delivery.api.openapi.controller.CidadeControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.CidadeDTOAssembler;
import com.sistema.delivery.api.v1.assembler.CidadeDTODisassembler;
import com.sistema.delivery.api.v1.assembler.CidadeInputDisassembler;
import com.sistema.delivery.api.v1.model.CidadeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//MediaType customizado
@RestController
@RequestMapping(path = "/v1/cidade/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi{

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeRepositoryService cidadeService;

	/*INJEÇÕES DAS DEPENDENCIAS DOS ASSEMBLERS*/
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssemblers;
	
	@Autowired
	private CidadeDTODisassembler cidadeDTODisassembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@CheckSecurity.Cidades.PodeConsultar
	//@Deprecated
	@GetMapping
	public CollectionModel<CidadeDTO> listar()
	{
		List<Cidade> todasCidades = cidadeRepository.findAll();
		return cidadeDTOAssemblers.toCollectionModel(todasCidades);
	}
	
	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping("{id}")
	public CidadeDTO retornarId(@PathVariable Long id)
	{
		Cidade cidade = cidadeService.buscarOuFalhar(id); 
		return cidadeDTOAssemblers.toModel(cidade);
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@PostMapping
	public CidadeDTO adiciona(@RequestBody CidadeInputDTO cidadeInputDTO)
	{
		try  
		{
			Cidade cidade = cidadeDTODisassembler.toDomainObject(cidadeInputDTO);
			
			cidade = cidadeService.adiciona(cidade);
			
			CidadeDTO cidadeDTO = cidadeDTOAssemblers.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
			return cidadeDTO;
		}catch(EstadoNaoEncontradoException e)
		{
			throw new NegocioExeption(e.getMessage(), e); 
		}
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@DeleteMapping("{id}")
	public void remova(@PathVariable Long id)
	{
		try
		{
		cidadeService.remova(id);
		}catch(CidadeNaoEncontradaException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@PutMapping("{id}") 
	public CidadeDTO alteraNaoParcial(@PathVariable Long id, 
			@RequestBody @Valid CidadeInputDTO cidadeInputDTO)	
	{
		try
		{
		Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
		cidadeInputDisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);
		cidadeAtual = cidadeService.adiciona(cidadeAtual);
		return cidadeDTOAssemblers.toModel(cidadeAtual);
		}catch(EstadoNaoEncontradoException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	}
//	
//	@PatchMapping("{id}")
//	public Cidade alteraParcial(
//			@PathVariable Long id, 
//			@RequestBody Map<String, Object> campos)
//	{
//		Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
//		
//		campos.forEach((nomePropriedade, valorPropriedade) ->{
//			ObjectMapper objectMapper = new ObjectMapper();
//			Cidade cidadeOrigem = objectMapper.convertValue(campos, Cidade.class);
//			
//			Field field = ReflectionUtils.findField(Cidade.class, nomePropriedade);
//			field.setAccessible(true);
//			Object novoValor = ReflectionUtils.getField(field, cidadeOrigem);
//			
//			ReflectionUtils.setField(field, cidadeAtual, novoValor);
//		});
//		 
//		return alteraNaoParcial(id, cidadeAtual);
//	}
	
	
	 
}
