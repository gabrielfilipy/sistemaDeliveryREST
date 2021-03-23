package com.sistema.delivery.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.exception.EntidadeEmUsoException;
import com.sistema.delivery.api.domain.exception.FormaPagamentoNaoExisteExeption;
import com.sistema.delivery.api.domain.exception.NegocioExeption;
import com.sistema.delivery.api.domain.model.Pagamento;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.repository.PagamentoRepository;
import com.sistema.delivery.api.domain.service.FormaPagamentoService;
import com.sistema.delivery.api.model.input.FormaPagamentoInputDTO;
import com.sistema.delivery.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.FormaPagamentoDTOAssembler;
import com.sistema.delivery.api.v1.assembler.FormaPagamentoDTODisassembler;
import com.sistema.delivery.api.v1.model.FormaPagamentoDTO;

@RestController
@RequestMapping(path = "/v1/forma-pagamento/", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi{

	@Autowired 
	private PagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoAssembler;
	
	@Autowired
	private FormaPagamentoDTODisassembler formaPagamentoDTODisassembler;
	
	@CheckSecurity.FormasPagamento.PodeConsultar
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request)
	{ 
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if(dataUltimaAtualizacao != null)
		{
			//Retorna um número de segundo desde 1970 até a data da última atualização que estiver buscando
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag))
		{
			return null;
		}
		
		List<Pagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
		
		CollectionModel<FormaPagamentoDTO> formasPagamentosModel = 
			    formaPagamentoAssembler.toCollectionModel(todasFormasPagamentos);
		
		//Gerar um hash de foma de pagamento
		
		return ResponseEntity.ok()
				/* Diretiva que faz com que o navegador implemente a tal representção por 10 segundos, por exemplo
				 * Cache-Control: max-age=10. Depois de 10 segundos ela se torna velha, daí será preciso buscar novamente.
				 */
				
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				//Nenhum cache pode armazenar a respota
				//.cacheControl(CacheControl.noStore())
				.body(formasPagamentosModel);
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar
	@PostMapping
	public FormaPagamentoDTO adicionar(@RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO)
	{
		Pagamento pagamento = formaPagamentoDTODisassembler.toDomainObject(formaPagamentoInputDTO);
		return formaPagamentoAssembler.toModel(formaPagamentoService.adiciona(pagamento));
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar
	@PutMapping("{id}")
	public FormaPagamentoDTO atualizar(@PathVariable Long id, @RequestBody FormaPagamentoInputDTO pagamento)
	{
		Pagamento pagamentoAtual = formaPagamentoService.existeOuNao(id);
		formaPagamentoDTODisassembler.toCopyDomainObject(pagamento, pagamentoAtual);
		
		try {	
			
			return formaPagamentoAssembler.toModel(formaPagamentoService.adiciona(pagamentoAtual));
			
		} catch (FormaPagamentoNaoExisteExeption e) {
			throw new NegocioExeption(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar
	@DeleteMapping("{id}")
	public void remova(@PathVariable Long id)
	{
		try
		{
		formaPagamentoService.remova(id);
		}catch(FormaPagamentoNaoExisteExeption e)
		{
			throw new NegocioExeption(e.getMessage(), e);
		}
	}

	@CheckSecurity.FormasPagamento.PodeConsultar
	@GetMapping("{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
	ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
			
	ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
	
	String eTag = "0";
	
	OffsetDateTime dataAtualizacao = formaPagamentoRepository
			.getDataAtualizacaoById(formaPagamentoId);
	
	if (dataAtualizacao != null) {
		eTag = String.valueOf(dataAtualizacao.toEpochSecond());
	}
	
	if (request.checkNotModified(eTag)) {
		return null;
	}
	
	Pagamento formaPagamento = formaPagamentoService.existeOuNao(formaPagamentoId);
	
	FormaPagamentoDTO formaPagamentoModel = formaPagamentoAssembler.toModel(formaPagamento);
	
	return ResponseEntity.ok()
			.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
			.eTag(eTag)
			.body(formaPagamentoModel);
	}
	
}
