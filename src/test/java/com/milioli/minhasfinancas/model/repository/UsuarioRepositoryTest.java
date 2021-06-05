package com.milioli.minhasfinancas.model.repository;

import com.milioli.minhasfinancas.TestAnnotations;
import com.milioli.minhasfinancas.utils.usuario.UsuarioTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.milioli.minhasfinancas.model.entity.Usuario;

public class UsuarioRepositoryTest extends TestAnnotations {

	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveRetornarVerdadeiroQuandoHouverUsuarioCadastradoEmail() {
		
		// cenário
		Usuario usuario = UsuarioTestUtils.constroiUsuarioSemId();

		entityManager.persist(usuario);

		// ação
		Boolean result = repository.existsByEmail("gabriel@email.com");
		
		// verificação
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoEmail() {
		repository.deleteAll();
		
		Boolean result = repository.existsByEmail("gabriel@email.com");

		Assertions.assertThat(result).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		Usuario usuario = UsuarioTestUtils.constroiUsuarioSemId();
		final Usuario novoUsuario = repository.save(usuario);
		Assertions.assertThat(novoUsuario.getId()).isNotNull();
	}

	@Test
	public void deveBuscarUmUsuarioPorEmail() {

		Usuario usuario = UsuarioTestUtils.constroiUsuarioSemId();
		entityManager.persist(usuario);

		final Usuario result = repository.findByEmail("gabriel@email.com");

		Assertions.assertThat(result).isNotNull();
	}

	@Test
	public void deveRetornarNullAoBuscarUmUsuarioPorEmailInexistenteEmBaseDeDados() {
		final Usuario result = repository.findByEmail("gabriel@email.com");

		Assertions.assertThat(result).isNull();
	}

}
