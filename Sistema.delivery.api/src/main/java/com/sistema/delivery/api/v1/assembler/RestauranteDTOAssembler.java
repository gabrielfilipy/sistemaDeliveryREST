package com.sistema.delivery.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.RestauranteController;
import com.sistema.delivery.api.v1.model.CozinhaDTO;
import com.sistema.delivery.api.v1.model.RestauranteDTO;

@Component
public class RestauranteDTOAssembler  extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public RestauranteDTOAssembler() {
        super(RestauranteController.class, RestauranteDTO.class);
    }
	
	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
	    modelMapper.map(restaurante, restauranteModel);
	    
	    if (sistemaSecurity.podeConsultarRestaurantes()) {
	    restauranteModel.add(sistemaLink.linkToRestaurantes("restaurantes"));
	    }
	    
	    if (sistemaSecurity.podeGerenciarCadastroRestaurantes()) {
	    if (restaurante.ativacaoPermitida()) {
	        restauranteModel.add(
	        		sistemaLink.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
	    }

	    if (restaurante.inativacaoPermitida()) {
	        restauranteModel.add(
	        		sistemaLink.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
	    }
	    }

	    
	    if (sistemaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
	    if (restaurante.aberturaPermitida()) {
	        restauranteModel.add(
	        		sistemaLink.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
	    }

	    if (restaurante.fechamentoPermitido()) {
	        restauranteModel.add(
	        		sistemaLink.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
	    }
	    
	    }
	    
	    if (sistemaSecurity.podeConsultarRestaurantes()) {
	    restauranteModel.add(sistemaLink.linkToProdutos(restaurante.getId(), "produtos"));
	    }
	    
	    if (sistemaSecurity.podeConsultarCozinhas()) {
	    restauranteModel.getCozinha().add(
	    		sistemaLink.linkToCozinha(restaurante.getCozinha().getId()));
	    }
	    
	    
	    if (sistemaSecurity.podeConsultarCidades()) {
	    if (restauranteModel.getEndereco() != null 
	            && restauranteModel.getEndereco().getCidade() != null) {
	        restauranteModel.getEndereco().getCidade().add(
	        		sistemaLink.linkToCidade(restaurante.getEndereco().getCidade().getId()));
	    }
	    }
	    
	    
	    if (sistemaSecurity.podeConsultarRestaurantes()) {
	    restauranteModel.add(sistemaLink.linkToRestauranteFormasPagamento(restaurante.getId(), 
	            "formas-pagamento"));
	    }
	    
	    
	    if (sistemaSecurity.podeGerenciarCadastroRestaurantes()) {
	    restauranteModel.add(sistemaLink.linkToRestauranteResponsaveis(restaurante.getId(), 
	            "responsaveis"));
	    }
	    
	    return restauranteModel;
	}
	
	/*Conversão simples de uma lista de um tipo para outro*/
	@Override 
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		 CollectionModel<RestauranteDTO> collection = super.toCollectionModel(entities);
		     
		if (sistemaSecurity.podeConsultarRestaurantes()) {
		   collection.add(sistemaLink.linkToRestaurantes());
		}
		
        return collection;
    } 
	
}
