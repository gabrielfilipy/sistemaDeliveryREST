package com.sistema.delivery.api.v1.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.core.security.PodeConsultarCozinhas;
import com.sistema.delivery.api.core.security.PodeEditarCozinhas;
import com.sistema.delivery.api.domain.exception.CozinhaNaoEncontradaException;
import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.repository.CozinhaRepository;
import com.sistema.delivery.api.domain.service.CozinhaRepositoryService;
import com.sistema.delivery.api.model.input.CozinhaInputDTO;
import com.sistema.delivery.api.openapi.controller.CozinhaControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.CozinhaDTOAssembler;
import com.sistema.delivery.api.v1.assembler.CozinhaDTODisassembler;
import com.sistema.delivery.api.v1.model.CozinhaDTO;


@RestController
@RequestMapping(path = "/v1/cozinha/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi{
 
	//Trazendo uma instancia de logger capaz de registrar logger. Adicionando a classe que será registrado esse logger
	private static final Logger logger = LoggerFactory.getLogger(CozinhaController.class);
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaRepositoryService cozinhaService;
	
	/*INJEÇÃO DOS ASSEMBLERS*/
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaDTODisassembler cozinhaDTODisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@CheckSecurity.Cozinhas.PodeConsultar
	@GetMapping
	public PagedModel<CozinhaDTO> listar(@PageableDefault Pageable pageable)
	{
		logger.info("Consultando cozinhas com páginas de {} registros ", pageable.getPageSize());
		
		Page<Cozinha> cozinhaLista = cozinhaRepository.findAll(pageable);
		PagedModel<CozinhaDTO> cozinhaPagedModel = pagedResourcesAssembler
				.toModel(cozinhaLista, cozinhaDTOAssembler);
		return cozinhaPagedModel;
	}
	
	@CheckSecurity.Cozinhas.PodeConsultar
	@GetMapping("{id}")
	public CozinhaDTO retornaId(@PathVariable Long id)
	{
		Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
		return cozinhaDTOAssembler.toModel(cozinha); 
	}
	
	@CheckSecurity.Cozinhas.PodeEditar
	@PostMapping
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO)
	{
		try
		{
			Cozinha cozinha = cozinhaDTODisassembler.toDomainObject(cozinhaInputDTO);
		return cozinhaDTOAssembler.toModel(cozinhaService.adiciona(cozinha)); 
		
		}catch(CozinhaNaoEncontradaException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	}
	 
	@CheckSecurity.Cozinhas.PodeEditar
	@DeleteMapping("{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id)
	{
		try
		{
		cozinhaService.remova(id);
		}catch(CozinhaNaoEncontradaException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
		}
	
	@CheckSecurity.Cozinhas.PodeEditar
	/*Por padrão se der tudo certo ele retorna um Status OK automaticamente*/
	@PutMapping("{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputDTO cozinha)
	{
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id", "estado_id");
		return cozinhaDTOAssembler.toModel(cozinhaService.adiciona(cozinhaAtual));
	} 
	
	@GetMapping("consultar-por-nome")
	public List<Cozinha> listarPorNome(String nome)
	{
		return cozinhaRepository.consultaPorNome(nome);
	} 
	
}
