package com.milioli.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milioli.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// Optional<Usuario> findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
