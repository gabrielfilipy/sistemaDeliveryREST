package com.sistema.delivery.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sistema.delivery.api.domain.model.Estado;

public class CidadeMixinModule{

	@JsonIgnore
	private Estado estado;
	
}
