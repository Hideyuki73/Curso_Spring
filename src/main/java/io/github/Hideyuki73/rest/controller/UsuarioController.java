package io.github.Hideyuki73.rest.controller;

import io.github.Hideyuki73.domain.entity.Usuario;
import io.github.Hideyuki73.domain.exception.SenhaInvalidaException;
import io.github.Hideyuki73.domain.security.JwtService;
import io.github.Hideyuki73.domain.service.impl.UsuarioServiceImpl;
import io.github.Hideyuki73.rest.dto.CredenciaisDTO;
import io.github.Hideyuki73.rest.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar (@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO) {
        try {
            Usuario usuario = Usuario.builder().login(credenciaisDTO.getLogin()).senha(credenciaisDTO.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
