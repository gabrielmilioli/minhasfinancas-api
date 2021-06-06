package com.milioli.minhasfinancas.resource;

import com.milioli.minhasfinancas.exceptions.AutenticacaoException;
import com.milioli.minhasfinancas.model.entity.Lancamento;
import com.milioli.minhasfinancas.model.entity.Usuario;
import com.milioli.minhasfinancas.resource.dto.UsuarioDto;
import com.milioli.minhasfinancas.service.LancamentoService;
import com.milioli.minhasfinancas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService service;

    private final LancamentoService lancamentoService;

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDto dto) {
        try {
            return ResponseEntity.ok(service.autenticar(dto.getEmail(), dto.getSenha()));
        } catch (AutenticacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDto dto) {
        Usuario usuario = UsuarioDto.buildEntity(dto);

        try {
            return new ResponseEntity(service.salvar(usuario), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldoUsuario(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(lancamentoService.obterSaldoPorUsuario(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
