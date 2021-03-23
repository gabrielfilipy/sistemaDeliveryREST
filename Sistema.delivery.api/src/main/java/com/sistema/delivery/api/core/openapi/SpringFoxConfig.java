package com.sistema.delivery.api.core.openapi;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.amazonaws.services.applicationinsights.model.Problem;
import com.fasterxml.classmate.TypeResolver;
import com.sistema.delivery.api.exceptionhandler.Problema;
import com.sistema.delivery.api.openapi.model.CidadesModelOpenApi;
import com.sistema.delivery.api.openapi.model.CozinhasModelOpenApi;
import com.sistema.delivery.api.openapi.model.EstadosModelOpenApi;
import com.sistema.delivery.api.openapi.model.GruposModelOpenApi;
import com.sistema.delivery.api.openapi.model.LinksModelOpenApi;
import com.sistema.delivery.api.openapi.model.PageableModelOpenApi;
import com.sistema.delivery.api.openapi.model.PedidosResumoModelOpenApi;
import com.sistema.delivery.api.openapi.model.PermissoesModelOpenApi;
import com.sistema.delivery.api.openapi.model.ProdutosModelOpenApi;
import com.sistema.delivery.api.openapi.model.RestaurantesBasicoModelOpenApi;
import com.sistema.delivery.api.openapi.model.UsuariosModelOpenApi;
import com.sistema.delivery.api.v1.model.CidadeDTO;
import com.sistema.delivery.api.v1.model.CozinhaDTO;
import com.sistema.delivery.api.v1.model.EstadoDTO;
import com.sistema.delivery.api.v1.model.GrupoDTO;
import com.sistema.delivery.api.v1.model.PedidoDTO;
import com.sistema.delivery.api.v1.model.PedidoResumoDTO;
import com.sistema.delivery.api.v1.model.PermissaoDTO;
import com.sistema.delivery.api.v1.model.ProdutoDTO;
import com.sistema.delivery.api.v1.model.RestauranteBasicoDTO;
import com.sistema.delivery.api.v1.model.UsuarioDTO;
import com.sistema.delivery.api.v2.model.CidadeDTOV2;
import com.sistema.delivery.api.v2.openapi.CozinhaControllerV2OpenApi;
import com.sistema.delivery.api.v2.openapi.model.CidadesModelV2OpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

	/*
	 * O SpringFox irá escanear toda aplicação para gerar o JSON
	 */
	
	@Bean
	public Docket apiDocketV1()
	{
		var typeResolver = new TypeResolver();
		
		//Docket é uma classe do Spring que representa a configuração da API pra gerar definição usando a especificação da API
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1")
				//Intanciando um Builder para selecionar os endpointer que serão expostos
				.select()
				//Selecionando os controladores selecionados do projeto
				.apis(RequestHandlerSelectors.basePackage("com.sistema.delivery.api"))
				.paths(PathSelectors.ant("/v1/**"))
				.build()
				.useDefaultResponseMessages(false)
				//Descrevendo códigos de status de respostas de forma global
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
	            .additionalModels(typeResolver.resolve(Problema.class))
	            .ignoredParameterTypes(ServletWebRequest.class,
	            		URL.class, URI.class, URLStreamHandler.class, Resource.class, InputStream.class
	            		)
	            
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(PagedModel.class, CozinhaDTO.class), 
	            		CozinhasModelOpenApi.class))
				.apiInfo(apiInfoV1())
				.alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(Page.class, PedidoResumoDTO.class), 
	            		PedidosResumoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(CollectionModel.class, CidadeDTO.class), 
	            		CidadesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
				        typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
				        EstadosModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, GrupoDTO.class),
					    GruposModelOpenApi.class))

				.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, PermissaoDTO.class),
					        PermissoesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class),
					    PedidosResumoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),
					    ProdutosModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class),
					    RestaurantesBasicoModelOpenApi.class))

					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),
					        UsuariosModelOpenApi.class))
				
				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()))	
				
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
				        new Tag("Grupos", "Gerencia os grupos de usuários"),
				        new Tag("Cozinhas", "Gerencia as cozinhas"),
				        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
				        new Tag("Pedidos", "Gerencia os pedidos"),
				        new Tag("Restaurantes", "Gerencia os restaurantes"),
				        new Tag("Estados", "Gerencia os estados"),
				        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
				        new Tag("Usuários", "Gerencia os usuários"),
				        new Tag("Estatísticas", "Estatísticas da AlgaFood"),
				        new Tag("Permissões", "Gerencia as permissões"));
	}
	
	//Configurando o suporte do Oauth2 para a documentação
	//Ou seja, estou descrevendo a tecnica que usei para descrever a API
	private SecurityScheme securityScheme()
	{
		return new OAuthBuilder()
				.name("Sistema")
				.grantTypes(grantTypes())
				.scopes(scopes()).build();
	}
	
	private SecurityContext securityContext()
	{
		var securityReference = SecurityReference.builder()
				//Será necessário referenciar com o mesmo nome atribuido no esquema OAuthBuilder()
				.reference("Sistema")
				.scopes(scopes().toArray(new AuthorizationScope[0])).build();
		
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(securityReference))
				//Mapeando quais os caminhos protegidos pelo SecurityScheme
				.forPaths(PathSelectors.any()).build();
	}
	
	private List<GrantType> grantTypes()
	{
		return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
	}
	
	private List<AuthorizationScope> scopes()
	{
		return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"), 
				new AuthorizationScope("WRITE", "Acesso de leitura"));
	}
	
	@Bean
	public Docket apiDocketV2()
	{
		var typeResolver = new TypeResolver();
		
		//Docket é uma classe do Spring que representa a configuração da API pra gerar definição usando a especificação da API
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V2")
				//Intanciando um Builder para selecionar os endpointer que serão expostos
				.select()
				//Selecionando os controladores selecionados do projeto
				.apis(RequestHandlerSelectors.basePackage("com.sistema.delivery.api"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
				.useDefaultResponseMessages(false)
				//Descrevendo códigos de status de respostas de forma global
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
	            .additionalModels(typeResolver.resolve(Problema.class))
	            .ignoredParameterTypes(ServletWebRequest.class,
	            		URL.class, URI.class, URLStreamHandler.class, Resource.class, InputStream.class
	            		)
	            
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
				
				.apiInfo(apiInfoV2())
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(PagedModel.class, CidadeDTOV2.class),
					    CozinhaControllerV2OpenApi.class))

					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, CidadeDTOV2.class),
					        CidadesModelV2OpenApi.class))

					.apiInfo(apiInfoV2())
					        
					.tags(new Tag("Cidades", "Gerencia as cidades"),
					        new Tag("Cozinhas", "Gerencia as cozinhas"));
				
	}
	
	//Mapeamento caminhos para servir arquivos estaticos SpringFox swagger-ui. Por exemplo HTML 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		
		//Mais um mapeamento
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	//Uma lista de dois estatus globais
	private List<ResponseMessage> globalGetResponseMessages()
	{
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceito pelo consumidor")
					.build()
				);
	}
	
	private ApiInfo apiInfoV1()
	{
		return new ApiInfoBuilder()
				.title("Sistema de delivery")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Monet System", "http://www.monetsystem.com", "monetsystem@gmail.com"))
				.build();
	}
	
	private ApiInfo apiInfoV2()
	{
		return new ApiInfoBuilder()
				.title("Sistema de delivery")
				.description("API aberta para clientes e restaurantes")
				.version("2")
				.contact(new Contact("Monet System", "http://www.monetsystem.com", "monetsystem@gmail.com"))
				.build();
	}
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
	    return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .message("Erro interno no servidor")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.NOT_ACCEPTABLE.value())
	                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
	                .message("Requisição recusada porque o corpo está em um formato não suportado")
	                .build()
	        );
	}

	private List<ResponseMessage> globalDeleteResponseMessages() {
	    return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .message("Erro interno no servidor")
	                .responseModel(new ModelRef("Problema"))
	                .build()
	        );
	}
	
}
