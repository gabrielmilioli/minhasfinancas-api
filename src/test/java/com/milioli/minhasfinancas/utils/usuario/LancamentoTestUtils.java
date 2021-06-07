package com.milioli.minhasfinancas.utils.usuario;

import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.entity.Usuario;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;
import com.milioli.minhasfinancas.model.enums.TipoLancamento;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoTestUtils {

    public static Long ID = 1L;
    public static String DESCRICAO = "Gabriel";
    public static Integer MES = 1;
    public static Integer ANO = 2021;
    public static Usuario USUARIO = Mockito.mock(Usuario.class);
    public static BigDecimal VALOR = BigDecimal.TEN;
    public static TipoLancamento TIPO = TipoLancamento.DESPESA;
    public static StatusLancamento STATUS = StatusLancamento.PENDENTE;
    public static LocalDate DATA_CADASTRO = LocalDate.now();

    public static Lancamento constroiLancamentoSemId() {
        return Lancamento.builder()
                .descricao(DESCRICAO)
                .mes(MES)
                .ano(ANO)
                .usuario(USUARIO)
                .valor(VALOR)
                .tipo(TIPO)
                .status(STATUS)
                .dataCadastro(DATA_CADASTRO)
                .build();
    }

    public static Lancamento constroiLancamentoComId() {
        Lancamento lancamento = constroiLancamentoSemId();
        lancamento.setId(ID);
        return lancamento;
    }
}
