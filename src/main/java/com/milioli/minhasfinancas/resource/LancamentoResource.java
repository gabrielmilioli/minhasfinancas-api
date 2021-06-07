package com.milioli.minhasfinancas.resource;

import com.milioli.minhasfinancas.exceptions.RegraNegocioException;
import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.enums.StatusLancamento;
import com.milioli.minhasfinancas.resource.dto.AtualizaStatusLancamentoDto;
import com.milioli.minhasfinancas.resource.dto.LancamentoDto;
import com.milioli.minhasfinancas.service.LancamentoService;
import com.milioli.minhasfinancas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {

    private final LancamentoService service;

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDto dto) {
        Lancamento lancamento = LancamentoDto.fromRepresentation(dto, usuarioService);

        try {
            return new ResponseEntity(service.salvar(lancamento), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDto dto) {
        final Lancamento lancamento = service.getById(id);
        Lancamento fromRepresentation = LancamentoDto.fromRepresentation(dto, lancamento, usuarioService);

        try {
            return new ResponseEntity(service.atualizar(fromRepresentation), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        final Lancamento lancamento = service.getById(id);

        try {
            service.deletar(lancamento);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
                                 @RequestParam(value = "mes", required = false) Integer mes,
                                 @RequestParam(value = "ano", required = false) Integer ano,
                                 @RequestParam(value = "usuario", required = false) Long idUsuario) {

        Lancamento lancamentoFiltro = new Lancamento(descricao, mes, ano, Optional.ofNullable(idUsuario)
                .map(usuarioService::getById).orElse(null));

        return ResponseEntity.ok(service.buscar(lancamentoFiltro));
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusLancamentoDto dto) {
        final Lancamento lancamento = service.getById(id);

        try {
            service.atualizarStatus(lancamento, Optional.ofNullable(dto.getStatus())
                    .map(StatusLancamento::valueOf).orElse(null));
            return ResponseEntity.accepted().build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
