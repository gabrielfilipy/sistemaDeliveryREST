package com.sistema.delivery.api.core.storage;

import java.io.InputStream;
import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("sistemadelivery.storage")
public class StorageProperties {
	
	//Classe que representa todas as propriedades relacionadas à uploads de arvios do aplication.properties
	private Local local = new Local();
	private S3 s3 = new S3();
	//Configurando para quando não for especificado o tipo, então seta o LOCAL para armazenar arquivos
	private TipoStorage tipo = TipoStorage.LOCAL;
	
	public enum TipoStorage
	{
		LOCAL, S3
	}
	
	@Getter
	@Setter
	public class Local 
	{
		
		private Path diretorioFotos;
		
	}
	
	@Getter
	@Setter
	public class S3
	{
		
		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		private Regions regiao;
		private String diretorioFotos;
	
	}

}
