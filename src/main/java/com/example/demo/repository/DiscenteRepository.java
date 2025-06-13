package com.example.demo.repository;


import com.example.demo.data.entity.Discente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscenteRepository extends JpaRepository<Discente, Long> {

    @Query(value = "SELECT * FROM discenti WHERE nome = ?1 AND cognome = ?2 AND matricola = ?3", nativeQuery = true)
    Discente findByNomeAndCognomeAndMatricola(String nome, String cognome, Integer matricola);

    @Query(value = "SELECT * FROM discenti ORDER BY nome ASC", nativeQuery = true)
    List<Discente> ordina_by_nome_asc();

    @Query(value = "SELECT * FROM discenti ORDER BY nome DESC", nativeQuery = true)
    List<Discente> ordina_by_nome_desc();


}