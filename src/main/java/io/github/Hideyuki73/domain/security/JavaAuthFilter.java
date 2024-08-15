package io.github.Hideyuki73.domain.security;

import io.github.Hideyuki73.domain.service.impl.UsuarioServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JavaAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UsuarioServiceImpl usuarioService;

    public JavaAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException { //Intercepta uma requisicao
        String autorization = httpServletRequest.getHeader("Authorization");//armazena a requisicao

        if(autorization != null && autorization.startsWith("Bearer")) {
            String token = autorization.split(" ")[1];
            boolean tokenValido = jwtService.tokenValido(token);//separa o token

            if(tokenValido) {//valida o token
                String loginUsuario = jwtService.obterLogin(token);
                UserDetails usuario=  usuarioService.loadUserByUsername(loginUsuario);
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)); //Para "falar" que o token pego e uma autenticacao web
                SecurityContextHolder.getContext().setAuthentication(user); //Joga para o contexto do web security
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
