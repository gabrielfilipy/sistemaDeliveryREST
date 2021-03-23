package com.sistema.delivery.api.v1.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sistema.delivery.api.core.data.PageWrapper;
import com.sistema.delivery.api.core.data.PageableTranslator;
import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.filtragem.PedidoFiltra;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.domain.repository.PedidoRepository;
import com.sistema.delivery.api.domain.service.EmissaoPedidoService;
import com.sistema.delivery.api.infraestrutura.spec.PedidoSpec;
import com.sistema.delivery.api.model.input.PedidoInput;
import com.sistema.delivery.api.openapi.controller.PedidoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.PedidoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.PedidoInputDisassembler;
import com.sistema.delivery.api.v1.assembler.PedidoResumoDTOAssembler;
import com.sistema.delivery.api.v1.model.PedidoDTO;
import com.sistema.delivery.api.v1.model.PedidoResumoDTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(path = "/v1/pedidos/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi{
 
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService pedidoRepositoryService;
	
	@Autowired
	private PedidoDTOAssembler pedidoDTOAssembler;
	
	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
//	@GetMapping
//	//TODO não exija o campo. Consumidor da API pode escolher passar ou não o campo
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos)
//	{
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		List<PedidoResumoDTO> pedidosResumoDTO = pedidoResumoDTOAssembler.toCollection(pedidos);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosResumoDTO);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		//TODO Filtra tudo exceto: 
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if(StringUtils.isNotBlank(campos))
//		{
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}
	
	@CheckSecurity.Pedidos.PodePesquisar
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar nas respotas separados por vírgulas"
				, name = "campos", paramType = "query", type = "string")
	})
	@GetMapping
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFiltra filtro, @PageableDefault(size = 10) Pageable pageable)
	{
		Pageable pageableTraduzido = traduzir(pageable);
	    
	    Page<Pedido> pedidosPage = pedidoRepository.findAll(
	            PedidoSpec.usandoFiltro(filtro), pageableTraduzido);
	    
	    pedidosPage = new PageWrapper<>(pedidosPage, pageable);
	    
	    return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoDTOAssembler);
	}
	
	@CheckSecurity.Pedidos.PodeBuscar
	@Override
	@GetMapping("{codigo}")
	public PedidoDTO buscar(@PathVariable String codigo) 
	{
		Pedido pedido = pedidoRepositoryService.buscarOuFalhar(codigo);
		return pedidoDTOAssembler.toModel(pedido);
	}
	
	/*Emitindo um pedido*/
	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@RequestBody PedidoInput pedidoInput)
	{
		try
		{
		Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
		
		//TODO pegar usário autenticado
		novoPedido.setCliente(new Usuario());
		novoPedido.getCliente().setId(sistemaSecurity.getUsuarioId());
		
		novoPedido = emissaoPedidoService.emitir(novoPedido);
		return pedidoDTOAssembler.toModel(novoPedido);
		
		}catch(EntidadeNaoExisteException e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}	
			
	}
	
	//Traduzir Pageable
	public Pageable traduzir(Pageable pageable)
	{
		var mapeamento = Map.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
				
				);
		return PageableTranslator.translater(pageable, mapeamento);
	}
	
}
