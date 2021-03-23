package com.sistema.delivery.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Cozinha;
import com.sistema.delivery.api.domain.model.FotoProduto;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.RestauranteProdutoFotoController;
import com.sistema.delivery.api.v1.model.CozinhaDTO;
import com.sistema.delivery.api.v1.model.FotoProdutoDTO;

@Component
public class FotoProdutoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public FotoProdutoDTOAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }
	
	@Override
	public FotoProdutoDTO toModel(FotoProduto fotoProduto)
	{
		FotoProdutoDTO fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoDTO.class);
        
		// Quem pode consultar restaurantes, também pode consultar os produtos e fotos
	    if (sistemaSecurity.podeConsultarRestaurantes()) {
        fotoProdutoModel.add(sistemaLink.linkToFotoProduto(
                fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));
        
        fotoProdutoModel.add(sistemaLink.linkToProduto(
                fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
	    }
        
        return fotoProdutoModel;
	}
	 
}
