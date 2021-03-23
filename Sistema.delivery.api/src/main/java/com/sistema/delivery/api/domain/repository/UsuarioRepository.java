package com.sistema.delivery.api.domain.repository;

import java.util.Optional;

import com.sistema.delivery.api.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
}
