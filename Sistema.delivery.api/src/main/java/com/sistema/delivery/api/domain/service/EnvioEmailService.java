package com.sistema.delivery.api.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	//Class de representação
	@Getter
	@Builder
	class Mensagem
	{
		
		/* Isso impossibilita ter destinatarios identicos
		 * A anotação @Singular ele singulariza o destinatário
		 */
		@Singular
		private Set<String> destinatarios;
		
		
		private String assunto;
		
		
		private String corpo;
		
		@Singular("variavel")
		private Map<String, Object> variaveis;
	
	}
}
