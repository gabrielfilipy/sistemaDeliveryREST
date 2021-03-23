package com.sistema.delivery.api.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.sistema.delivery.api.core.storage.StorageProperties.TipoStorage;
import com.sistema.delivery.api.domain.service.FotoStorageService;
import com.sistema.delivery.api.infraestrutura.storage.LocalFotoStorageService;
import com.sistema.delivery.api.infraestrutura.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

	//Passar as credenciais
	@Autowired
	private StorageProperties storageProperties;
	
	//Produz uma instancia para Bean Spring
	@Bean
	public AmazonS3 amazonS3()
	{
		var credencial = new BasicAWSCredentials(
				storageProperties.getS3().getIdChaveAcesso(), storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credencial))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService()
	{
		if(TipoStorage.S3.equals(storageProperties.getTipo()))
		{
			return new S3FotoStorageService();
		}else
		{
			return new LocalFotoStorageService();
		}
	}
	
}
