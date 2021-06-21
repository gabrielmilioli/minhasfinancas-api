package com.milioli.minhasfinancas.service.impl;

import com.milioli.minhasfinancas.exceptions.RegraNegocioException;
import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;
import com.milioli.minhasfinancas.model.enums.TipoLancamento;
import com.milioli.minhasfinancas.model.repository.LancamentoRepository;
import com.milioli.minhasfinancas.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private final LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lancamento getById(Long id) {
        return repository.getById(id);
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        lancamento.setStatus(StatusLancamento.PENDENTE);
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamento) {
        Example<Lancamento> example = Example.of(lancamento,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        return atualizar(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {
        if (Objects.isNull(lancamento.getDescricao()) || lancamento.getDescricao().isBlank()) {
            throw new RegraNegocioException(MSG_ERRO_INFORME_DESCRICAO_VALIDA);
        }

        if (Objects.isNull(lancamento.getMes())) {
            throw new RegraNegocioException(MSG_ERRO_INFORME_MES_VALIDO);
        }

        if (Objects.isNull(lancamento.getAno())) {
            throw new RegraNegocioException(MSG_ERRO_INFORME_ANO_VALIDO);
        }

        if (Objects.isNull(lancamento.getUsuario())) {
            throw new RegraNegocioException(MSG_ERRO_INFORME_USUARIO_VALIDO);
        }

        if (Objects.isNull(lancamento.getValor()) || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new RegraNegocioException(MSG_ERRO_INFORME_VALOR_VALIDO);
        }

        if (Objects.isNull(lancamento.getTipo())) {
            throw new RegraNegocioException(MSG_ERRO_INFORME_TIPO_VALIDO);
        }

        if (Objects.isNull(lancamento.getStatus())) {
            throw new RegraNegocioException(MSG_ERRO_INFORME_STATUS_VALIDO);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long idUsuario) {
        final BigDecimal receitas = Optional.ofNullable(repository
                .obterSaldoPorTipoLancamentoEUsuarioEStatus(idUsuario, TipoLancamento.RECEITA, StatusLancamento.EFETIVADO))
                .orElse(BigDecimal.ZERO);
        final BigDecimal despesas = Optional.ofNullable(repository
                .obterSaldoPorTipoLancamentoEUsuarioEStatus(idUsuario, TipoLancamento.DESPESA, StatusLancamento.EFETIVADO))
                .orElse(BigDecimal.ZERO);

        return receitas.subtract(despesas);
    }
}
