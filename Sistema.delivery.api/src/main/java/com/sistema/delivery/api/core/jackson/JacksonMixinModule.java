package com.sistema.delivery.api.core.jackson;
 
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.model.mixin.CidadeMixinModule;
import com.sistema.delivery.api.model.mixin.CozinhaMixinModule;
import com.sistema.delivery.api.model.mixin.RestauranteMixin;

@Component
public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule()
	{
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixinModule.class); 
		setMixInAnnotation(Cozinha.class, CozinhaMixinModule.class); 
	}
}
