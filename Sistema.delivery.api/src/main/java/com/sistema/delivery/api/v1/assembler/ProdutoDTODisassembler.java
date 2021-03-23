package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Produto;
import com.sistema.delivery.api.model.input.ProdutoInputDTO;
import com.sistema.delivery.api.v1.model.ProdutoDTO;

@Component
public class ProdutoDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoInputDTO produtoDTO)
	{
		return modelMapper.map(produtoDTO, Produto.class);
	}
	
	public void copy(ProdutoInputDTO produtoInputDTO, Produto produto)
	{
		modelMapper.map(produtoInputDTO ,produto);
	}
}
