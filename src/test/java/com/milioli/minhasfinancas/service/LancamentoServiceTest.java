package com.milioli.minhasfinancas.service;

import com.milioli.minhasfinancas.TestAnnotations;
import com.milioli.minhasfinancas.exceptions.RegraNegocioException;
import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;
import com.milioli.minhasfinancas.model.repository.LancamentoRepository;
import com.milioli.minhasfinancas.service.impl.LancamentoServiceImpl;
import com.milioli.minhasfinancas.utils.usuario.LancamentoTestUtils;
import com.milioli.minhasfinancas.utils.usuario.UsuarioTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.milioli.minhasfinancas.service.LancamentoService.*;
import static com.milioli.minhasfinancas.service.UsuarioService.MSG_ERRO_EMAIL_JA_CADASTRADO;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LancamentoServiceTest extends TestAnnotations {

    @SpyBean
    LancamentoServiceImpl service;

    @MockBean
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        final Lancamento lancamentoSalvo = LancamentoTestUtils.constroiLancamentoComId();

        Mockito.doNothing().when(service).validar(lancamentoASalvar);
        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        final Lancamento lancamento = service.salvar(lancamentoASalvar);

        assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
    }

    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroValidacao() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();

        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

        catchThrowableOfType(() ->
                service.salvar(lancamentoASalvar), RegraNegocioException.class);

        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComDescricaoInvalida() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setDescricao(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_DESCRICAO_VALIDA);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComMesInvalido() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setMes(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_MES_VALIDO);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComAnoInvalido() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setAno(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_ANO_VALIDO);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComUsuarioInvalido() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setUsuario(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_USUARIO_VALIDO);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComValorInvalido() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setValor(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_VALOR_VALIDO);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComValorMenorOuIgualAZero() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setValor(BigDecimal.ZERO);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_VALOR_VALIDO);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComTipoInvalido() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setTipo(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_TIPO_VALIDO);
    }

    @Test
    public void deveRetornarErroAoValidarLancamentoComStatusInvalido() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();
        lancamentoASalvar.setStatus(null);

        final Throwable exception = Assertions.catchThrowable(() ->
                service.validar(lancamentoASalvar));

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage(MSG_ERRO_INFORME_STATUS_VALIDO);
    }

    @Test
    public void deveAtualizarUmLancamento() {
        final Lancamento lancamentoSalvo = LancamentoTestUtils.constroiLancamentoComId();
        lancamentoSalvo.setStatus(StatusLancamento.EFETIVADO);

        Mockito.doNothing().when(service).validar(lancamentoSalvo);
        Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

        final Lancamento lancamento = service.atualizar(lancamentoSalvo);

        assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
        assertThat(lancamento.getStatus()).isEqualTo(lancamentoSalvo.getStatus());
    }

    @Test
    public void naoDeveAtualizarUmLancamentoSemId() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();

        catchThrowableOfType(() ->
                service.atualizar(lancamentoASalvar), NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveDeletarUmLancamento() {
        final Lancamento lancamentoSalvo = LancamentoTestUtils.constroiLancamentoComId();

        service.deletar(lancamentoSalvo);

        assertThat(entityManager.find(Lancamento.class, lancamentoSalvo.getId())).isNull();
        Mockito.verify(repository).delete(lancamentoSalvo);
    }

    @Test
    public void naoDeveDeletarUmLancamentoSemId() {
        final Lancamento lancamentoASalvar = LancamentoTestUtils.constroiLancamentoSemId();

        catchThrowableOfType(() ->
                service.deletar(lancamentoASalvar), NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).delete(lancamentoASalvar);
    }

    @Test
    public void deveBuscarLancamentos() {
        final Lancamento lancamentoFiltro = LancamentoTestUtils.constroiLancamentoComId();
        List<Lancamento> lancamentos = Collections.singletonList(lancamentoFiltro);

        Mockito.when(repository.findAll(any(Example.class))).thenReturn(lancamentos);

        final List<Lancamento> resultados = service.buscar(lancamentoFiltro);

        assertThat(resultados).isNotEmpty()
                .hasSize(lancamentos.size())
                .contains(lancamentoFiltro);
    }

    @Test
    public void deveAtualizarOStatusDeUmLancamento() {
        final Lancamento lancamento = LancamentoTestUtils.constroiLancamentoComId();
        final StatusLancamento novoStatus = StatusLancamento.EFETIVADO;

        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

        service.atualizarStatus(lancamento, novoStatus);

        assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
    }

    @Test
    public void naoDeveAtualizarOStatusDeUmLancamentoSemId() {
        final Lancamento lancamento = LancamentoTestUtils.constroiLancamentoSemId();
        final StatusLancamento novoStatus = StatusLancamento.EFETIVADO;

        catchThrowableOfType(() ->
                service.atualizarStatus(lancamento, novoStatus), NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).save(lancamento);
    }

    @Test
    public void naoDeveAtualizarOStatusDeUmLancamentoSeOStatusForNulo() {
        final Lancamento lancamento = LancamentoTestUtils.constroiLancamentoComId();

        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamento);

        catchThrowableOfType(() ->
                service.atualizarStatus(lancamento, null), RegraNegocioException.class);
    }

    @Test
    public void deveObterUmLancamentoPorId() {
        final Lancamento lancamento = LancamentoTestUtils.constroiLancamentoComId();

        Mockito.when(repository.getById(LancamentoTestUtils.ID)).thenReturn(lancamento);

        Lancamento resultado = service.getById(LancamentoTestUtils.ID);

        assertThat(resultado).isNotNull();
    }

    @Test
    public void naoDeveObterUmLancamentoPorId() {
        Mockito.when(repository.getById(LancamentoTestUtils.ID)).thenReturn(null);

        catchThrowableOfType(() ->
                service.getById(LancamentoTestUtils.ID), RegraNegocioException.class);
    }

}
