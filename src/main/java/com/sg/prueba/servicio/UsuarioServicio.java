package com.sg.prueba.servicio;

import com.sg.prueba.dto.UsuarioRegistroDto;
import com.sg.prueba.modelo.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioServicio extends UserDetailsService {

    public Usuario RegistrarUsuario(UsuarioRegistroDto registroDto);
    public List<Usuario> listarUsuarios();
}
