package com.milioli.minhasfinancas.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milioli.minhasfinancas.TestResourceAnnotations;
import com.milioli.minhasfinancas.model.entity.Usuario;
import com.milioli.minhasfinancas.resource.dto.UsuarioDto;
import com.milioli.minhasfinancas.service.LancamentoService;
import com.milioli.minhasfinancas.service.UsuarioService;
import com.milioli.minhasfinancas.utils.usuario.UsuarioTestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UsuarioResource.class)
public class UsuarioResourceTest extends TestResourceAnnotations {

    static final String API_PATH = ApiPathUtils.USUARIO.path;
    static final String AUTENTICAR_PATH = API_PATH.concat("/autenticar");

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService service;

    @MockBean
    LancamentoService lancamentoService;

    @Test
    public void deveAutenticarUmUsuario() throws Exception {
        UsuarioDto dto = UsuarioDto.builder()
                .email(UsuarioTestUtils.EMAIL)
                .senha(UsuarioTestUtils.SENHA)
                .build();

        Usuario usuario = UsuarioTestUtils.constroiUsuarioComId();

        Mockito.when(service.autenticar(UsuarioTestUtils.EMAIL, UsuarioTestUtils.SENHA)).thenReturn(usuario);

        String json = new ObjectMapper().writeValueAsString(dto);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(AUTENTICAR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
    }

}
