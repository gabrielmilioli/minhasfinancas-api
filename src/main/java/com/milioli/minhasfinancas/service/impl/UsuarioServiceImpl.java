package com.milioli.minhasfinancas.service.impl;

import com.milioli.minhasfinancas.exceptions.AutenticacaoException;
import com.milioli.minhasfinancas.exceptions.RegraNegocioException;
import com.milioli.minhasfinancas.model.entity.Usuario;
import com.milioli.minhasfinancas.model.repository.UsuarioRepository;
import com.milioli.minhasfinancas.service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        final Usuario usuario = repository.findByEmail(email);

        if (Objects.isNull(usuario)) {
            throw new AutenticacaoException(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
        }

        if (Boolean.FALSE.equals(usuario.getSenha().equals(senha))) {
            throw new AutenticacaoException(MSG_ERRO_SENHA_INVALIDA);
        }

        return usuario;
    }

    @Override
    @Transactional
    public Usuario salvar(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new RegraNegocioException(MSG_ERRO_EMAIL_JA_CADASTRADO);
        }
    }

}
