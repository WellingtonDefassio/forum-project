package com.forum.config.security;

import com.forum.modelo.Usuario;
import com.forum.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthenticacaoService implements UserDetailsService {

    UsuarioRepository usuarioRepository;

    public AuthenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(username);

        if (!optionalUsuario.isPresent()) {
            throw new UsernameNotFoundException("usuario nao encontrado");
        }
        System.out.println("usuario encontrado " + optionalUsuario.get().getEmail());
        return optionalUsuario.get();
    }
}
