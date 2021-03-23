package com.sistema.delivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.sistema.delivery.api.v1.model.ProdutoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProdutoModel")
@Data
public class ProdutosModelOpenApi {

	private ProdutosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("ProdutosEmbeddedModel")
    @Data
    public class ProdutosEmbeddedModelOpenApi {
        
        private List<ProdutoDTO> produtos;
        
    }    
    
}
