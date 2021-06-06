package com.milioli.minhasfinancas.resource.dto;

import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;
import com.milioli.minhasfinancas.model.enums.TipoLancamento;
import com.milioli.minhasfinancas.service.UsuarioService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Builder
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
/*
    public static LancamentoDto toRepresentation(Lancamento lancamento) {
        return LancamentoDto.builder()
                .id(lancamento.getId())
                .descricao(lancamento.getDescricao())
                .mes(lancamento.getMes())
                .ano(lancamento.getAno())
                .usuario(lancamento.getUsuario().getId())
                .valor(lancamento.getValor())
                .dataCadastro(lancamento.getDataCadastro())
                .tipo(lancamento.getTipo().toString())
                .status(lancamento.getStatus().toString())
                .build();
    }

    public static Lancamento fromRepresentation(LancamentoDto dto, Lancamento.LancamentoBuilder builder) {
        return builder
                .descricao(dto.getDescricao())
                .mes(dto.getMes())
                .ano(dto.getAno())
                .usuario(usuarioService.getById(dto.getUsuario()))
                .valor(dto.getValor())
                .dataCadastro(dto.getDataCadastro())
                .tipo(TipoLancamento.valueOf(dto.getTipo()))
                .status(StatusLancamento.valueOf(dto.getStatus()))
                .build();
    }
*/
}
