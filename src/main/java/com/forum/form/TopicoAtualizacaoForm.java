package com.forum.form;

import com.forum.modelo.Topico;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicoAtualizacaoForm {


    @NotNull  @NotEmpty @Length(min = 5)
    private String titulo;
    @NotNull @NotEmpty @Length(min = 10)
    private String mesagem;



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

    public Topico atualizar(Topico topico) {
         topico.setMensagem(this.mesagem);
         topico.setTitulo(this.titulo);

         return topico;
    }
}
