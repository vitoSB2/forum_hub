package com.vitor.forum_hub.domain.topico;

import com.vitor.forum_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    @Column(name = "nomecurso")
    private String nomeCurso;
    private String autor;
    @Column(name = "datacriacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.nomeCurso = dados.nomeCurso();
        this.autor = dados.autor();
        this.dataCriacao = LocalDateTime.now();
        this.estado = Estado.NAO_RESPONDIDO;
    }

    public void atualizar(DadosAtualizacaoTopico dados) {
        if(dados.titulo() != null)
            this.titulo = dados.titulo();

        if(dados.mensagem() != null)
            this.mensagem = dados.mensagem();

        if(dados.nomeCurso() != null)
            this.nomeCurso = dados.nomeCurso();
    }
}
