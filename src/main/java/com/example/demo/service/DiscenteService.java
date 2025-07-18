package com.example.demo.service;

import com.example.demo.data.entity.Discente;
import com.example.demo.data.entity.Docente;
import com.example.demo.repository.DiscenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscenteService {


    @Autowired
    DiscenteRepository discenteRepository;

    public Discente getByNomeAndCognomeAndMatricola(String nome, String cognome, Integer matricola) {
        return discenteRepository.findByNomeAndCognomeAndMatricola(nome, cognome, matricola);
    }

    public List<Discente> findAll() {
        return discenteRepository.findAll();
    }

    public Discente get(Long id) {
        return discenteRepository.findById(id).orElseThrow();
    }

    public Discente save(Discente s) {
        return discenteRepository.save(s);
    }

    public void delete(Long id) {
        discenteRepository.deleteById(id);
    }

    // Query custom
    public List<Discente> ordina_by_nome_asc() {
        return discenteRepository.ordina_by_nome_asc();
    }

    public List<Discente> ordina_by_nome_desc() {
        return discenteRepository.ordina_by_nome_desc();
    }

    //
    public List<Discente> findAllByIds(List<Long> ids) {
        return discenteRepository.findAllById(ids);
    }


}
