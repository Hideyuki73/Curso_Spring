package io.github.Hideyuki73;

import io.github.Hideyuki73.domain.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
        return Jwts.builder().setSubject(usuario.getLogin()).signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        JwtService service = context.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("admin").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);
    }
}
