package com.sistema.delivery.api.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	FotoRecuperada recuperar(String nomeArquivo);
	
	void armazenar(NovaFoto novaFoto);

	void remover(String nomeArquivo);
	
	default String gerarNomeArquivo(String nomeOriginal)
	{
		return UUID.randomUUID().toString() + "-" + nomeOriginal;
	}
	
	@Builder
	@Getter
	class NovaFoto
	{
		private String nomeArquivo;
		private String contenType;
		private InputStream inputStream;
	}
	
	@Builder
	@Getter
	public class FotoRecuperada
	{
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl()
		{
			return url != null;
		}
		
		public boolean temInputStream()
		{
			return inputStream != null;
		}
	}
	
}
