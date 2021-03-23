package com.sistema.delivery.api.infraestrutura.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.sistema.delivery.api.core.storage.StorageProperties;
import com.sistema.delivery.api.domain.service.FotoStorageService;

//@Service
public class LocalFotoStorageService implements FotoStorageService{

	/*
	 * Tratamento de armazenamento de arquivo
	 */
	
	//Injetando uma propriedade
	//@Value("${Sistema.delivery.storage.local.diretorio-fotos}")
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
	
		try {
			//System.out.println("Passou!!!!!!");
			//Caminho onde será armazenado arquivo
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			
			//Copiando os dados donde está recebendo para o caminho 'arquivoPath'
			FileCopyUtils.copy(novaFoto.getInputStream(), 
					Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo)
	{
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			//Caminho onde estará armazenado o arquivo que será removido
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			Files.deleteIfExists(arquivoPath);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
			
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
					.inputStream(Files.newInputStream(arquivoPath)).build();
					
			return fotoRecuperada;
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar esse arquivo.", e);
		}
	}
	
}
