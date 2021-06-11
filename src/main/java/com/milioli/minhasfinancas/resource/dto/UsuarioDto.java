package com.milioli.minhasfinancas.resource.dto;

import com.milioli.minhasfinancas.model.entity.Usuario;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    public static Usuario buildEntity(UsuarioDto dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();
    }

}
