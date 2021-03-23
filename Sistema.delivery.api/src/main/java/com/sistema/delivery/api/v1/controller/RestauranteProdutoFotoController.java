package com.sistema.delivery.api.v1.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.cognitoidp.model.HttpHeader;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.core.storage.StorageProperties;
import com.sistema.delivery.api.domain.exception.EntidadeNaoExisteException;
import com.sistema.delivery.api.domain.model.FotoProduto;
import com.sistema.delivery.api.domain.model.Produto;
import com.sistema.delivery.api.domain.service.CatalogoFotoProdutoService;
import com.sistema.delivery.api.domain.service.FotoStorageService;
import com.sistema.delivery.api.domain.service.FotoStorageService.FotoRecuperada;
import com.sistema.delivery.api.domain.service.ProdutoRepositoryService;
import com.sistema.delivery.api.model.input.FotoProdutoInput;
import com.sistema.delivery.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.sistema.delivery.api.v1.assembler.FotoProdutoDTOAssembler;
import com.sistema.delivery.api.v1.model.FotoProdutoDTO;

import lombok.Builder;
import lombok.Getter;

@RestController
@RequestMapping(path = "/v1/restaurante/{restauranteId}/produto/{produtoId}/foto",
produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi{

	@Autowired
	private ProdutoRepositoryService produtoRepositoryService;
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private FotoProdutoDTOAssembler fotoProdutoDTOAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
//	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public void atualizarFoto(@PathVariable Long restauranteId, 
//			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput)
//	{ 
//		
//		var arquivoNome = UUID.randomUUID().toString() + "-" + fotoProdutoInput.getArquivo().getOriginalFilename();
//		var caminho = Path.of("C:/Users/filip/OneDrive/Imagens/Catagalo", arquivoNome);
//		System.out.println(fotoProdutoInput.getDescricao());
//		System.out.println(caminho);
//		System.out.println(fotoProdutoInput.getArquivo().getContentType());
//		
//		try {
//			fotoProdutoInput.getArquivo().transferTo(caminho);
//		} catch (Exception e) {
//			
//			throw new RuntimeException();
//		}
//	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput,
			@RequestPart(required = true) MultipartFile arquivo) throws IOException
	{ 
		//MultipartFile arquivo = fotoProdutoInput.getArquivo();
		Produto produto = produtoRepositoryService.existeOuNao(produtoId, restauranteId);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(arquivo.getContentType());
		metadata.setContentLength(arquivo.getSize());
		
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(metadata.getContentLength());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
		
		return fotoProdutoDTOAssembler.toModel(fotoSalva);
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping()
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId)
	{
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		    
		return fotoProdutoDTOAssembler.toModel(fotoProduto);
	}
	
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> servir(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException
	{
		
		/*
		 * Recebendo os argumento na String acceptHeader.
		 * Feito isso eu instancio um MediaType real da foto do produto
		 */
		try
		{
			
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
			//Pesso o ContentType e faço um parse para termos um objeto MediaType. Ou seja, temos uma MediaType da foto que será armazenada no disco.
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType()); 
			//Fazendo um parse para uma lista de MediaType. Ou seja, podemos passar mais de um elementos
			List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatilibidadeMediaType(mediaTypeFoto, mediaTypeAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if(fotoRecuperada.temUrl())
			{
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}else
			{
			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
			
		}catch(EntidadeNaoExisteException e)
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId)
	{
		catalogoFotoProdutoService.excluir(restauranteId, produtoId);
	}

	private void verificarCompatilibidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativeis = mediaTypeAceitas.stream()
				//Validação: se pelo menos um for verdadeiro JPGE ou PNG no caso.
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativeis)
		{ 
			throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
		}
	}

}
