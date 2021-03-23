package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.RestauranteController;
import com.sistema.delivery.api.v1.model.RestauranteApenasNomeDTO;

@Component
public class RestauranteApenasNomeModelAssembler  extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDTO>{

	@Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SistemaLink sistemaLink;
    
    @Autowired
    private SistemaSecurity sistemaSecurity;    
    
    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeDTO.class);
    }
    
    @Override
    public RestauranteApenasNomeDTO toModel(Restaurante restaurante) {
        RestauranteApenasNomeDTO restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        if (sistemaSecurity.podeConsultarRestaurantes()) {
        restauranteModel.add(sistemaLink.linkToRestaurantes("restaurantes"));
        }
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteApenasNomeDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
    	CollectionModel<RestauranteApenasNomeDTO> collectionModel = super.toCollectionModel(entities);
        
        if (sistemaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(sistemaLink.linkToRestaurantes());
        }
                
        return collectionModel;
    }   
	
}
