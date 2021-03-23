package com.sistema.delivery.api.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.model.FotoProduto;
import com.sistema.delivery.api.domain.repository.ProdutoRepository;
import com.sistema.delivery.api.domain.service.FotoStorageService.NovaFoto;
import com.sistema.delivery.api.infraestrutura.storage.FotoProdutoNaoEncontradaException;

@Service
public class CatalogoFotoProdutoService {

	/*
	 * Salva as informações da imagem, mas não faz tratamento de armazenamento de arquivo
	 */
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream inputStream)
	{
		Long restauranteId = foto.getProduto().getRestaurante().getId();
		Long produtoId = foto.getProduto().getId();
		String nomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		
		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent())
		{
			//Extraindo o objeto do Optional, porque ele é um arquivo. nomeArquivoExistente vai receber o valor de fotoExistente.
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			//Excluir foto, se existir 
			produtoRepository.delete(fotoExistente.get());
		}
		//Gerando um novo nome para o arquivo. Para evitar nome repitivos.
		foto.setNomeArquivo(nomeArquivo);
		//Primeiro salva as informações do arquivo para depois salvar o arquivo
		foto =  produtoRepository.save(foto);
		
		//Avisando o JPA para descarregar toda a pilha
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contenType(foto.getContentType())
				.inputStream(inputStream)
				.build(); 
				
		if(nomeArquivoExistente != null)
		{
		fotoStorageService.remover(nomeArquivoExistente);
		}
		
		fotoStorageService.armazenar(novaFoto);
		
		return foto;
	}
	
	@Transactional
	public void excluir(Long restauranteId, Long produtoId)
	{
		FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);
		
		produtoRepository.delete(foto);
		produtoRepository.flush();
		
		fotoStorageService.remover(foto.getNomeArquivo());
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId)
	{
		return produtoRepository.findFotoById(restauranteId, produtoId)
	            .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}
	
}
