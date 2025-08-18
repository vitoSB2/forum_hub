package com.vitor.forum_hub.controller;

import com.vitor.forum_hub.domain.usuario.DadosAutenticacao;
import com.vitor.forum_hub.domain.usuario.Usuario;
import com.vitor.forum_hub.domain.usuario.UsuarioRepository;
import com.vitor.forum_hub.infra.security.TokenDadosJWT;
import com.vitor.forum_hub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authenticaon = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario)  authenticaon.getPrincipal());

        return ResponseEntity.ok(new TokenDadosJWT(tokenJWT));
    }

}
