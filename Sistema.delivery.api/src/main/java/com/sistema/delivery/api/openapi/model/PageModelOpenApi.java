package com.sistema.delivery.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelOpenApi {

	@ApiModelProperty(example = "10", value = "Quantidades de registros por páginas")
	private Long size;
	
	@ApiModelProperty(example = "500", value = "Total de registros")
	private Long totalElements;
	
	@ApiModelProperty(example = "5", value = "Total de páginas")
	private Long totalPages;
	
	@ApiModelProperty(example = "0", value = "Número de páginas(começa em 0)")
	private Long number;
	
}
