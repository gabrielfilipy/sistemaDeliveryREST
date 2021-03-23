package com.sistema.delivery.api.openapi.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Links")
public class LinksModelOpenApi {

	private Linkmodel rel;
	
	@Getter
	@Setter
	@ApiModel("Link")
	private class Linkmodel 
	{
		private String href;
		private boolean templated;
	}
	
}
