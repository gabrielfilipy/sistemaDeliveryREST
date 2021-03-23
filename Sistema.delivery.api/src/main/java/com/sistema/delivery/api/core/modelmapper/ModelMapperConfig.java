package com.sistema.delivery.api.core.modelmapper;

import org.apache.tomcat.util.http.fileupload.MultipartStream.ItemInputStream;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Endereco;
import com.sistema.delivery.api.domain.model.ItemPedido;
import com.sistema.delivery.api.model.input.ItemPedidoInputDTO;
import com.sistema.delivery.api.v1.model.EnderecoDTO;
import com.sistema.delivery.api.v2.model.input.CidadeInputDTOV2;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper()
	{
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(CidadeInputDTOV2.class, Cidade.class)
		.addMappings(mapper -> mapper.skip(Cidade::setId));
		
	
		
		modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoToEnderecoModelTypeMap = 
				modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(), 
				(dest, value) -> dest.getCidade().setEstado(value)); 
		return modelMapper;
	}
	
}
