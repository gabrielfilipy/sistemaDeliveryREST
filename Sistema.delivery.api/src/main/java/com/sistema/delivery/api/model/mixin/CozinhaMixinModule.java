package com.sistema.delivery.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.delivery.api.domain.model.Restaurante;

public class CozinhaMixinModule {

	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();
	
}
