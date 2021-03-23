package com.sistema.delivery.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TipoDoProblema { 

	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"), 
	
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	
	MENSAGEM_INCONPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
	
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	
	ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
	
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	TipoDoProblema(String caminho, String title)
	{
		this.uri = "https://sistemadeliveryapi.com.br" + caminho;
		this.title = title;
	}
	
}
