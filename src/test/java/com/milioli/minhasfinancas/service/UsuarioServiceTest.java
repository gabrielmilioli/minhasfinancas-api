package com.milioli.minhasfinancas.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.milioli.minhasfinancas.exceptions.RegraNegocioException;
import com.milioli.minhasfinancas.model.entity.Usuario;
import com.milioli.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;

	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveValidarEmail() {
	    repository.deleteAll();
	    
	    service.validarEmail("gabriel@email.com");
	}
	
	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		 Usuario usuario = Usuario.builder()
				 .nome(null)
				 .email("gabriel@email.com")
				 .build();
		 repository.save(usuario);
		 
		 assertThrows(RegraNegocioException.class, () -> {
			 service.validarEmail("gabriel@email.com");
		 });
	}
	
}
