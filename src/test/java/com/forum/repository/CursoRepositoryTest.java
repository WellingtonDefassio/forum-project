package com.forum.repository;

import com.forum.modelo.Curso;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
        Curso html5 = new Curso();
        String nomeCurso = "HTML 5";
        html5.setNome(nomeCurso);
        em.persist(html5);

        Curso curso = cursoRepository.findByName(nomeCurso);
        Assert.notNull(curso);
        Assertions.assertEquals(nomeCurso, curso.getNome());

    }
    @Test
    void deveriaDarUmErroSeNaoEncontrarUmCursoPeloNome() {

        String nomeCurso = "HTML 5";
        Curso curso = cursoRepository.findByName(nomeCurso);
        Assert.isNull(curso);

    }
}