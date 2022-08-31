package com.forum.config.security;

import com.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${forum.jwt.expiration}")
    private String expiration;
    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToker(Authentication authenticate) {
        Usuario logado = (Usuario) authenticate.getPrincipal();
        Date hoje = new Date();
        Date expirationDate = new Date(hoje.getTime() + Long.parseLong(expiration));
        return Jwts.builder().setIssuer("API do Forum Alura")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret).compact();

    }

    public boolean isValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        long id = Long.parseLong(claims.getSubject());
        System.out.println(id);
        return id;
    }
}
