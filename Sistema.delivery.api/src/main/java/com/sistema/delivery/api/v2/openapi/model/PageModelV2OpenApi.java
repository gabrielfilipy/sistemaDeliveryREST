package com.sistema.delivery.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.sistema.delivery.api.v2.model.CidadeDTOV2;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class PageModelV2OpenApi {

	private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadesEmbeddedModelOpenApi {
        
        private List<CidadeDTOV2> cidades;
        
    }
	
}
