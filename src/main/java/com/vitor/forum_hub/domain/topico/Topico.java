package com.vitor.forum_hub.domain.topico;

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

    @Id
    private Long id;
    private String titulo;
    private String mensagem;
    private String nomeCurso;
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private Estado estado;

//    @Embedded
//    private Usuario autor;

}
