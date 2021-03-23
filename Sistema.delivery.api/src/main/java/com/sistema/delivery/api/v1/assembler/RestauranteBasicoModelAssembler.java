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
import com.sistema.delivery.api.v1.model.RestauranteBasicoDTO;

@Component
public class RestauranteBasicoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	    
	@Autowired
	private SistemaLink sistemaLink;
	    
	@Autowired
	private SistemaSecurity sistemaSecurity;  
	
	public RestauranteBasicoModelAssembler() {
	     super(RestauranteController.class, RestauranteBasicoDTO.class);
	}
	    
	@Override
	public RestauranteBasicoDTO toModel(Restaurante restaurante) {
		RestauranteBasicoDTO restauranteModel = createModelWithId(
	            restaurante.getId(), restaurante);
	        
	   modelMapper.map(restaurante, restauranteModel);
	       
	   if (sistemaSecurity.podeConsultarRestaurantes()) {
	   restauranteModel.add(sistemaLink.linkToRestaurantes("restaurantes"));
	   }   
	   
	   if (sistemaSecurity.podeConsultarCozinhas()) {
	   restauranteModel.getCozinha().add(
			   sistemaLink.linkToCozinha(restaurante.getCozinha().getId()));
	   }     
	   
	   return restauranteModel;
	 }
	    
	 @Override
	 public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		 CollectionModel<RestauranteBasicoDTO> collectionModel = super.toCollectionModel(entities);
		    
		 if (sistemaSecurity.podeConsultarRestaurantes()) {
		        collectionModel.add(sistemaLink.linkToRestaurantes());
		 }
		            
		 return collectionModel;
	 }   
	
}
