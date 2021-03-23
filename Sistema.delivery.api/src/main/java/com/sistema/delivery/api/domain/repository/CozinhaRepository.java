package com.sistema.delivery.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.delivery.api.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{

	/*Query customizada vinda de: src/main/resource/META-INF/orm.xml*/
	public List<Cozinha> consultaPorNome(String nome); 
}
