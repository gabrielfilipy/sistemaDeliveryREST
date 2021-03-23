package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.model.input.UsuarioInputDTO;
import com.sistema.delivery.api.v1.model.UsuarioDTO;

@Component
public class UsuarioDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInputDTO usuarioDTO)
	{
		return modelMapper.map(usuarioDTO, Usuario.class);
	}
	
	public void copy(UsuarioInputDTO usuarioDTO, Usuario usuario)
	{
		modelMapper.map(usuarioDTO, usuario);
	}
	
}
