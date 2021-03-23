package com.sistema.delivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.sistema.delivery.api.v1.model.GrupoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

	private GruposEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("GruposEmbeddedModel")
    @Data
    public class GruposEmbeddedModelOpenApi {
        
        private List<GrupoDTO> grupos;
        
    }   
	
}

