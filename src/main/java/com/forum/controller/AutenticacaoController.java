package com.forum.controller;

import com.forum.config.security.TokenService;
import com.forum.dto.TokenDto;
import com.forum.form.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

  private AuthenticationManager authenticationManager;
  private TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity autenticar(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();
        try {
            Authentication authenticate = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToker(authenticate);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
