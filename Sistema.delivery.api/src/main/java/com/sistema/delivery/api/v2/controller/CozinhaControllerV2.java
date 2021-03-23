package com.sistema.delivery.api.v2.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
import com.sistema.delivery.api.v2.assembler.CozinhaDTOAssemblerV2;
import com.sistema.delivery.api.v2.assembler.CozinhaDTODisassemblerV2;
import com.sistema.delivery.api.v2.model.CozinhaDTOV2;
import com.sistema.delivery.api.v2.model.input.CozinhaInputDTOV2;
import com.sistema.delivery.api.v2.openapi.CozinhaControllerV2OpenApi;

@RestController
@RequestMapping(path = "/v2/cozinha/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi{
 
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaRepositoryService cozinhaService;
	
	/*INJEÇÃO DOS ASSEMBLERS*/
	
	@Autowired
	private CozinhaDTOAssemblerV2 cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaDTODisassemblerV2 cozinhaDTODisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@GetMapping
	public PagedModel<CozinhaDTOV2> listar(@PageableDefault Pageable pageable)
	{
		Page<Cozinha> cozinhaLista = cozinhaRepository.findAll(pageable);
		PagedModel<CozinhaDTOV2> cozinhaPagedModel = pagedResourcesAssembler
				.toModel(cozinhaLista, cozinhaDTOAssembler);
		return cozinhaPagedModel;
	}
	
	@GetMapping("{id}")
	public CozinhaDTOV2 buscar(@PathVariable Long id)
	{
		Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
		return cozinhaDTOAssembler.toModel(cozinha); 
	}
	
	@PostMapping
	public CozinhaDTOV2 adicionar(@RequestBody @Valid CozinhaDTOV2 cozinhaInputDTO)
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
	
	/*Por padrão se der tudo certo ele retorna um Status OK automaticamente*/
	@PutMapping("{id}")
	public CozinhaDTOV2 atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputDTOV2 cozinha)
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
