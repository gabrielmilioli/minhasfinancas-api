package com.milioli.minhasfinancas.service;

import com.milioli.minhasfinancas.TestAnnotations;
import com.milioli.minhasfinancas.exceptions.AutenticacaoException;
import com.milioli.minhasfinancas.exceptions.RegraNegocioException;
import com.milioli.minhasfinancas.model.entity.Usuario;
import com.milioli.minhasfinancas.model.repository.UsuarioRepository;
import com.milioli.minhasfinancas.service.impl.UsuarioServiceImpl;
import com.milioli.minhasfinancas.utils.usuario.UsuarioTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.milioli.minhasfinancas.service.UsuarioService.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest extends TestAnnotations {

    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveValidarEmail() {
        when(repository.existsByEmail(anyString())).thenReturn(Boolean.FALSE);

        service.validarEmail(UsuarioTestUtils.EMAIL);
    }

    @Test
    public void deveRetornarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        when(repository.existsByEmail(anyString())).thenReturn(Boolean.TRUE);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validarEmail(UsuarioTestUtils.EMAIL));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_EMAIL_JA_CADASTRADO);
    }

    @Test
    public void deveValidarAutenticacao() {
        final Usuario usuario = UsuarioTestUtils.constroiUsuarioComId();
        when(repository.findByEmail(anyString())).thenReturn(usuario);

        final Usuario result = service.autenticar(usuario.getEmail(), usuario.getSenha());

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void deveRetornarErroQuandoNaoEncontrarUsuarioCadastradoComEmailInformado() {
        when(repository.findByEmail(anyString())).thenReturn(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.autenticar(UsuarioTestUtils.EMAIL, UsuarioTestUtils.SENHA));

        Assertions.assertThat(exception)
                .isInstanceOf(AutenticacaoException.class)
                .hasMessage(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
    }

    @Test
    public void deveRetornarErroQuandoSenhaForInvalida() {
        final Usuario usuario = UsuarioTestUtils.constroiUsuarioComId();
        when(repository.findByEmail(anyString())).thenReturn(usuario);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.autenticar(UsuarioTestUtils.EMAIL, "senhainvalida"));

        Assertions.assertThat(exception)
                .isInstanceOf(AutenticacaoException.class)
                .hasMessage(MSG_ERRO_SENHA_INVALIDA);
    }

    @Test
    public void deveSalvarUsuario() {
        final Usuario usuario = UsuarioTestUtils.constroiUsuarioComId();

        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        final Usuario novoUsuario = service.salvar(new Usuario());

        Assertions.assertThat(novoUsuario).isNotNull();
        Assertions.assertThat(novoUsuario.getId()).isEqualTo(usuario.getId());
        Assertions.assertThat(novoUsuario.getNome()).isEqualTo(usuario.getNome());
        Assertions.assertThat(novoUsuario.getEmail()).isEqualTo(usuario.getEmail());
        Assertions.assertThat(novoUsuario.getSenha()).isEqualTo(usuario.getSenha());
    }

    @Test
    public void deveRetornarErroQuandoNaoSalvarUsuarioComEmailJaCadastrado() {
        final Usuario usuario = UsuarioTestUtils.constroiUsuarioComId();

        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(UsuarioTestUtils.EMAIL);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.salvar(usuario));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class);

        Mockito.verify(repository, Mockito.never()).save(usuario);
    }

    @Test
    public void deveRetornarErroEValidarMsgQuandoTentarSalvarUsuarioComEmailJaCadastrado() {
        final Usuario usuario = UsuarioTestUtils.constroiUsuarioComId();

        when(repository.existsByEmail(anyString())).thenReturn(Boolean.TRUE);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.salvar(usuario));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_EMAIL_JA_CADASTRADO);
    }

}
