package com.vitor.forum_hub.controller;

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
        repository.save(topico);
        var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
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
        var topico = repository.findById(id);
        if(topico.isPresent()){
            topico.get().atualizar(dados);
            repository.save(topico.get());
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
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
}
