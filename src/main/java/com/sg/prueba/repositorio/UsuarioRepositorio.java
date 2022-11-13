package com.sg.prueba.repositorio;

import com.sg.prueba.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {
    public Usuario findByEmail(String email);
}
