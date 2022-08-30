package com.forum.form;

import com.forum.modelo.Curso;
import com.forum.modelo.Topico;
import com.forum.repository.CursoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicoForm {
    @NotNull @NotEmpty @Length(min = 5)
    private String titulo;
    @NotNull @NotEmpty @Length(min = 10)
    private String mesagem;
    @NotNull @NotEmpty
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMesagem() {
        return mesagem;
    }

    public void setMesagem(String mesagem) {
        this.mesagem = mesagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository cursoRepository) {
        Topico topico = new Topico();
        topico.setTitulo(titulo);
        topico.setMensagem(mesagem);
        Curso curso = cursoRepository.findByName(nomeCurso);
        topico.setCurso(curso);
        return topico;
    }
}
