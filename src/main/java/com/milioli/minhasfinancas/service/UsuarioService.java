package com.milioli.minhasfinancas.service;

import com.milioli.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	String MSG_ERRO_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	String MSG_ERRO_SENHA_INVALIDA = "Senha inválida";
	String MSG_ERRO_EMAIL_JA_CADASTRADO = "Já existe um usuário cadastrado com este e-mail";

	Usuario autenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void validarEmail(String email);

	Usuario getById(Long id);

}
