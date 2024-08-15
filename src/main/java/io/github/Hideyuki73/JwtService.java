package io.github.Hideyuki73;

import io.github.Hideyuki73.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {//gera o token para validar o login do usuario
        long expString = Long.valueOf(expiracao);
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder().setSubject(usuario.getLogin()).setExpiration(data).signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();
    }

    private Claims obterClaims (String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
    }

    public boolean tokenValido (String token) throws ExpiredJwtException {
        try{
            Claims claims = obterClaims(token);
            Date data = claims.getExpiration();
            LocalDateTime dataExpi = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(dataExpi);
        }catch (ExpiredJwtException e){
            return false;
        }
    }

    public String obterLogin(String login) throws ExpiredJwtException {
        return (String) obterClaims(login).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        JwtService service = context.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("admin").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.println("Token valido: " + isTokenValido);
        System.out.println(service.obterLogin(token));
    }
}
