package com.sistema.delivery.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Produto;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.RestauranteProdutoController;
import com.sistema.delivery.api.v1.model.ProdutoDTO;

@Component
public class ProdutoDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity; 
	
	public ProdutoDTOAssembler() {
	    super(RestauranteProdutoController.class, ProdutoDTO.class);
	}
	
	@Override
	public ProdutoDTO toModel(Produto produto)
	{
		ProdutoDTO produtoModel = createModelWithId(
	            produto.getId(), produto, produto.getRestaurante().getId());
	    
	    modelMapper.map(produto, produtoModel);
	    if (sistemaSecurity.podeConsultarRestaurantes()) {
	    produtoModel.add(sistemaLink.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

	    produtoModel.add(sistemaLink.linkToFotoProduto(
	            produto.getRestaurante().getId(), produto.getId(), "foto"));
	    }
	    return produtoModel;
	}
	
}
