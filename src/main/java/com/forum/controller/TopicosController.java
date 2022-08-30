package com.forum.controller;

import com.forum.dto.TopicoDetalhadoDto;
import com.forum.dto.TopicoDto;
import com.forum.form.TopicoAtualizacaoForm;
import com.forum.form.TopicoForm;
import com.forum.modelo.Topico;
import com.forum.repository.CursoRepository;
import com.forum.repository.TopicoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    TopicoRepository topicoRepository;
    CursoRepository cursoRepository;

    public TopicosController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping()
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 2) Pageable paginacao) {

        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoName(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }
    }

    @PostMapping()
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoForm.converter(this.cursoRepository);
        Topico topicoSave = topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoSave.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topicoSave));

    }

    @GetMapping("{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if(!topicoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        TopicoDetalhadoDto topicoDetalhadoDto = new TopicoDetalhadoDto(topicoOptional.get());

        return new ResponseEntity(topicoDetalhadoDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @Valid @RequestBody TopicoAtualizacaoForm topicoForm ){

        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if(!topicoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoForm.atualizar(topicoOptional.get());

        topicoRepository.save(topico);

        return ResponseEntity.ok(new TopicoDto(topico));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if(!topicoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.delete(topicoOptional.get());

        return ResponseEntity.ok().build();
    }

}
