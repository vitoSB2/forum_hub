package com.vitor.forum_hub.controller;

import com.vitor.forum_hub.domain.ValidacaoException;
import com.vitor.forum_hub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
//@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody DadosCadastroTopico dados, UriComponentsBuilder uriBuilder){
        var topico = new Topico(dados);
        if(testarSeJaExiste(topico.getTitulo(),topico.getMensagem())){
            repository.save(topico);
            var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
        }else{
            throw new ValidacaoException("Já existe um tópico com o mesmo título e mensagem no banco de dados");
        }
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar(@PageableDefault(size = 10) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosDetalhamentoTopico::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var topico = repository.findById(id);
        if(topico.isPresent())
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados){
        var topicoOptional = repository.findById(id);
        if(topicoOptional.isPresent()){
            var topico = topicoOptional.get();
            String titulo, mensagem;
            if(dados.titulo() == null)
                titulo = topico.getTitulo();
            else
                titulo = dados.titulo();

            if(dados.mensagem() == null)
                mensagem = topico.getMensagem();
            else
                mensagem = dados.mensagem();

            if(testarSeJaExiste(titulo, mensagem)){
                topico.atualizar(dados);
                repository.save(topico);
                return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
            }else{
                throw new ValidacaoException("Já existe um tópico com o mesmo título e mensagem no banco de dados");
            }
        }else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var topico = repository.findById(id);
        if(topico.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else
            return ResponseEntity.notFound().build();
    }

    private boolean testarSeJaExiste(String titulo, String mensagem){
        List<Topico> topicosComTituloIgual = repository.findByTitulo(titulo);
        System.out.println("testarSeJaExiste: ");
        topicosComTituloIgual.forEach(t -> System.out.println(t.getMensagem()));

        System.out.println(mensagem);

        for(Topico top: topicosComTituloIgual){
            if(top.getMensagem().equals(mensagem)){
                return false;
            }
        }
        return true;
    }
}
