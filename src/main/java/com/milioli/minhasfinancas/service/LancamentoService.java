package com.milioli.minhasfinancas.service;

import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;

import java.math.BigDecimal;
import java.util.List;

public interface LancamentoService {

    String MSG_ERRO_INFORME_DESCRICAO_VALIDA = "Informe uma descrição válida";
    String MSG_ERRO_INFORME_MES_VALIDO = "Informe um mês válido";
    String MSG_ERRO_INFORME_ANO_VALIDO = "Informe um ano válido";
    String MSG_ERRO_INFORME_USUARIO_VALIDO = "Informe um usuário válido";
    String MSG_ERRO_INFORME_VALOR_VALIDO = "Informe um valor válido";
    String MSG_ERRO_INFORME_TIPO_VALIDO = "Informe um tipo válido";
    String MSG_ERRO_INFORME_STATUS_VALIDO = "Informe um status válido";

    Lancamento getById(Long id);

    Lancamento salvar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    void validar(Lancamento lancamento);

    BigDecimal obterSaldoPorUsuario(Long id);

    Lancamento atualizarStatus(Lancamento lancamento, StatusLancamento status);

}
