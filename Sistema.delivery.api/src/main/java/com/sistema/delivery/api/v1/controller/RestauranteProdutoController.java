package com.sistema.delivery.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.model.Produto;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.repository.ProdutoRepository;
import com.sistema.delivery.api.domain.service.ProdutoRepositoryService;
import com.sistema.delivery.api.domain.service.RestauranteRepositoryServive;
import com.sistema.delivery.api.model.input.ProdutoInputDTO;
import com.sistema.delivery.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.ProdutoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.ProdutoDTODisassembler;
import com.sistema.delivery.api.v1.model.ProdutoDTO;

@RestController
@RequestMapping(path = "/v1/restaurante/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi{

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoRepositoryService produtoRepositoryService;
	
	@Autowired
	private RestauranteRepositoryServive restauranteRepositoryService;
	
	@Autowired
	private ProdutoDTOAssembler produtoDTOAssembler;
	
	@Autowired
	private ProdutoDTODisassembler produtoDTODisassembler;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<ProdutoDTO> listar(
			@PathVariable Long restauranteId, @RequestParam(required = false, defaultValue = "false") 
			Boolean incluirInativos)
	{
		Restaurante restaurante = restauranteRepositoryService.buscarOuFalhar(restauranteId);
		
		List<Produto> listaProdutos = null;
		
		if(incluirInativos)
		{
			listaProdutos = produtoRepository.findAtivoByRestaurante(restaurante);
		}else
		{
			listaProdutos = produtoRepository.findByRestaurante(restaurante);
		}
		return produtoDTOAssembler.toCollectionModel(listaProdutos);
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
	    Produto produto = produtoRepositoryService.existeOuNao(restauranteId, produtoId);
	        
	    return produtoDTOAssembler.toModel(produto);
	 }
	
	 @CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, 
			@RequestBody @Valid ProdutoInputDTO produtoInputDTO)
	{
		Restaurante restaurante = restauranteRepositoryService.buscarOuFalhar(restauranteId);
		Produto produto = produtoDTODisassembler.toDomainObject(produtoInputDTO);
		produto.setRestaurante(restaurante);
		
		produto = produtoRepositoryService.adicione(produto);
		
		return produtoDTOAssembler.toModel(produto);
		
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
	     @RequestBody @Valid ProdutoInputDTO produtoInput) {
	    Produto produtoAtual = produtoRepositoryService.existeOuNao(produtoId, restauranteId);
	        
	    produtoDTODisassembler.copy(produtoInput, produtoAtual);
	        
	    produtoAtual = produtoRepositoryService.adicione(produtoAtual);
	        
	    return produtoDTOAssembler.toModel(produtoAtual);
	 }	

}
