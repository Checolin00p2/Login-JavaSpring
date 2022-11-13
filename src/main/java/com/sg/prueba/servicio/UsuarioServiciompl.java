package com.sg.prueba.servicio;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.sg.prueba.dto.UsuarioRegistroDto;
import com.sg.prueba.modelo.Rol;
import com.sg.prueba.modelo.Usuario;
import com.sg.prueba.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UsuarioServiciompl implements UsuarioServicio{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private UsuarioRepositorio usuarioRepositorio;

    public UsuarioServiciompl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario RegistrarUsuario(UsuarioRegistroDto registroDto) {
        Usuario usuario = new Usuario(registroDto.getEmail(),
                passwordEncoder.encode(registroDto.getPassword()),
                Arrays.asList(new Rol("Admin")));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if(usuario==null){
            throw  new UsernameNotFoundException("Usuario o password Inválidos");
        }
        return new User(usuario.getEmail(),usuario.getPassword(),mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }
}
