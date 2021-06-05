package com.milioli.minhasfinancas.utils.usuario;

import com.milioli.minhasfinancas.model.entity.Usuario;

public class UsuarioTestUtils {

    public static Long ID = 1L;
    public static String NOME = "Gabriel";
    public static String EMAIL = "gabriel@email.com";
    public static String SENHA = "gabriel123";

    public static Usuario constroiUsuarioSemId() {
        return Usuario.builder()
                .nome(NOME)
                .email(EMAIL)
                .senha(SENHA)
                .build();
    }

    public static Usuario constroiUsuarioComId() {
        return Usuario.builder()
                .id(ID)
                .nome(NOME)
                .email(EMAIL)
                .senha(SENHA)
                .build();
    }
}
