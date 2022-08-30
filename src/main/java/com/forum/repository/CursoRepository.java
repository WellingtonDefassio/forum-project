package com.forum.repository;

import com.forum.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("select c from Curso c where c.nome = :nomeCurso")
    Curso findByName(@Param("nomeCurso") String nomeCurso);
}
