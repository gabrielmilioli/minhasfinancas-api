package com.milioli.minhasfinancas.resource.dto;

import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;
import com.milioli.minhasfinancas.model.enums.TipoLancamento;
import com.milioli.minhasfinancas.service.UsuarioService;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDto {

    private Long id;

    private String descricao;

    private Integer mes;

    private Integer ano;

    private Long usuario;

    private BigDecimal valor;

    private LocalDate dataCadastro;

    private String tipo;

    private String status;

    public static Lancamento fromRepresentation(LancamentoDto dto, UsuarioService usuarioService) {
        return Lancamento.builder()
                .descricao(dto.getDescricao())
                .mes(dto.getMes())
                .ano(dto.getAno())
                .usuario(usuarioService.getById(dto.getUsuario()))
                .valor(dto.getValor())
                .dataCadastro(dto.getDataCadastro())
                .tipo(TipoLancamento.valueOf(dto.getTipo()))
                .status(Optional.ofNullable(dto.getStatus()).map(StatusLancamento::valueOf).orElse(null))
                .build();
    }

    public static Lancamento fromRepresentation(LancamentoDto dto, Lancamento lancamento, UsuarioService usuarioService) {
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setMes(dto.getMes());
        lancamento.setAno(dto.getAno());
        lancamento.setUsuario(usuarioService.getById(dto.getUsuario()));
        lancamento.setValor(dto.getValor());
        lancamento.setDataCadastro(dto.getDataCadastro());
        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));

        if (Objects.nonNull(dto.getStatus())) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }

        return lancamento;
    }
}