package com.milioli.minhasfinancas.resource;

public enum ApiPathUtils {
    ROOT("/api"),
    USUARIO("/api/usuarios"),
    LANCAMENTO("/api/lancamentos");

    public final String path;
    
    ApiPathUtils(String path) {
        this.path = path;
    }
}
