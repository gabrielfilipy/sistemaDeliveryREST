package com.sistema.delivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.sistema.delivery.api.openapi.model.CidadesModelOpenApi.CidadeEmbeddedModelOpenApi;
import com.sistema.delivery.api.v1.model.CidadeDTO;
import com.sistema.delivery.api.v1.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi{
	
	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi
	{
		private List<CozinhaDTO> cozinhas;
	}
	
}
