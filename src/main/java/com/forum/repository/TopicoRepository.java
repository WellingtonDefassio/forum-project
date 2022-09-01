package com.forum.repository;

import com.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("select t from Topico t join t.curso c where c.nome = :nomeCurso")
    Page<Topico> findByCursoName(@Param("nomeCurso") String nomeCurso, Pageable pageable);

}
