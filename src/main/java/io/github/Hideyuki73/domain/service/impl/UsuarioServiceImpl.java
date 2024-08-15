package io.github.Hideyuki73.domain.service.impl;

import io.github.Hideyuki73.domain.entity.Usuario;
import io.github.Hideyuki73.domain.exception.SenhaInvalidaException;
import io.github.Hideyuki73.domain.repository.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Usuarios usuarioRepository;

    @Transactional
    public Usuario salvar (Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhaCorreta = passwordEncoder.matches(usuario.getSenha(), user.getPassword());
        if(senhaCorreta) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
