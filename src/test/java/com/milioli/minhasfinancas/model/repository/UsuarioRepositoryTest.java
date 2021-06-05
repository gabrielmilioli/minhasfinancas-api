package com.milioli.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.milioli.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveVerificarExistenciaEmail() {
		
		// cenário
		Usuario usuario = Usuario.builder()
			.nome("Gabriel")
			.email("gabriel@email.com")
			.build();
		repository.save(usuario);
		
		// ação
		Boolean result = repository.existsByEmail("gabriel@email.com");
		
		// verificação
		Assertions.assertThat(result).isTrue();
	}
	
}
