package com.sistema.delivery.api.core.security.authorizationserver;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.sistema.delivery.api.domain.model.Usuario;

import lombok.Getter;

@Getter
public class OauthUser extends User{

	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String fullName;
	
	public OauthUser(Usuario usuario, Collection<? extends GrantedAuthority> authority) {
		super(usuario.getEmail(), usuario.getSenha(), authority);
		this.userId = usuario.getId();
		this.fullName = usuario.getNome();
	}
	
}
