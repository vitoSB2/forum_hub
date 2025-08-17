package com.vitor.forum_hub.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, String autor, String nomeCurso, LocalDateTime dataCriacao, Estado estado) {
    public DadosDetalhamentoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getNomeCurso(), topico.getDataCriacao(), topico.getEstado());
    }
}
