package com.sistema.delivery.api.v1.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.exception.EstadoNaoEncontradoException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Estado;
import com.sistema.delivery.api.domain.repository.EstadoRepository;
import com.sistema.delivery.api.domain.service.EstadoRepositoryService;
import com.sistema.delivery.api.model.input.EstadoInputDTO;
import com.sistema.delivery.api.openapi.controller.EstadoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.EstadoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.EstadoDTODisassembler;
import com.sistema.delivery.api.v1.model.EstadoDTO;

@RestController
@RequestMapping(path = "/v1/estado/", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi{

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoRepositoryService estadoService;
	
	/*INJEÇÃO DE DEPENDENCIA DOS ASSEMBLERS*/
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@Autowired
	private EstadoDTODisassembler estadoDTODisassembler;
	
	@CheckSecurity.Estados.PodeConsultar
	@GetMapping
	public CollectionModel<EstadoDTO> listar() 
	{
		List<Estado> estados = estadoRepository.findAll();
		return estadoDTOAssembler.toCollectionModel(estados);
	} 
	
	@CheckSecurity.Estados.PodeConsultar
	@GetMapping("{id}")
	public EstadoDTO buscar(@PathVariable Long id)
	{
		return estadoDTOAssembler.toModel(estadoService.buscarOuFalhar(id)); 
	}
	
	@CheckSecurity.Estados.PodeEditar
	@PostMapping
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO)
	{
		try
		{
			Estado estado = estadoDTODisassembler.toDomainObject(estadoInputDTO);
		return estadoDTOAssembler.toModel(estadoService.adiciona(estado));
		}catch(EstadoNaoEncontradoException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	} 
	
	@CheckSecurity.Estados.PodeEditar
	@DeleteMapping("{id}")
	public void remover(@PathVariable Long id)
	{
		try {
			estadoService.remova(id);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioExeption(e.getMessage(), e);
		}
		
	}
	
	@CheckSecurity.Estados.PodeEditar
	@PutMapping("{id}") 
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInputDTO cidade)	
	{
		Estado estadoAtual = estadoService.buscarOuFalhar(id);
		BeanUtils.copyProperties(cidade, estadoAtual, "id");
		try
		{
		return estadoDTOAssembler.toModel(estadoService.adiciona(estadoAtual));
		}catch(EstadoNaoEncontradoException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	}

//	@PatchMapping("{id}")
//	public Estado alteraParcial(
//			@PathVariable Long id, 
//			@RequestBody Map<String, Object> campos)
//	{
//		Estado estadoAtual = estadoService.buscarOuFalhar(id);
//		 
//		campos.forEach((nomePropriedade, valorPropriedade) ->{
//			ObjectMapper objectMapper = new ObjectMapper();
//			Estado cidadeOrigem = objectMapper.convertValue(campos, Estado.class);
//			
//			Field field = ReflectionUtils.findField(Estado.class, nomePropriedade);
//			field.setAccessible(true);
//			Object novoValor = ReflectionUtils.getField(field, cidadeOrigem);
//			
//			ReflectionUtils.setField(field, estadoAtual, novoValor);
//		});
//		 
//		return alteraNaoParcial(id, estadoAtual);
//	}
	
}
