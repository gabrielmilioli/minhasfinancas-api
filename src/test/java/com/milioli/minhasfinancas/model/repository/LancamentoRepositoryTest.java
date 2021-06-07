package com.milioli.minhasfinancas.model.repository;

import com.milioli.minhasfinancas.TestAnnotations;
import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.milioli.minhasfinancas.utils.usuario.LancamentoTestUtils.constroiLancamentoSemId;
import static org.assertj.core.api.Assertions.assertThat;

public class LancamentoRepositoryTest extends TestAnnotations {

    @Autowired
    LancamentoRepository repository;

    public Lancamento persistirERetornarUmLancamento() {
        return entityManager.persist(constroiLancamentoSemId());
    }

    @Test
    public void deveSalvarUmLancamento() {
        Lancamento lancamento = constroiLancamentoSemId();

        final Lancamento novoLancamento = repository.save(lancamento);

        assertThat(novoLancamento).isNotNull();
    }

    @Test
    public void deveDeletarUmLancamento() {
        final Lancamento novoLancamento = persistirERetornarUmLancamento();

        repository.delete(novoLancamento);

        assertThat(entityManager.find(Lancamento.class, novoLancamento.getId())).isNull();
    }

    @Test
    public void deveAtualizarUmLancamento() {
        Lancamento novoLancamento = persistirERetornarUmLancamento();
        novoLancamento.setStatus(StatusLancamento.EFETIVADO);

        final Lancamento lancamentoAtualizado = repository.save(novoLancamento);

        assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.EFETIVADO);
    }

    @Test
    public void deveBuscarUmLancamentoPorId() {
        Lancamento novoLancamento = persistirERetornarUmLancamento();
        assertThat(repository.getById(novoLancamento.getId())).isNotNull();
    }

}
