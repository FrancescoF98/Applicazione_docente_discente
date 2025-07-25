package com.example.demo.service;

import com.example.demo.data.entity.Docente;
import com.example.demo.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocenteService {


    @Autowired
    DocenteRepository docenteRepository;



    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Docente get(Long id) {
        return docenteRepository.findById(id).orElseThrow();
    }

    public Docente getByNomeAndCognome(String nome, String cognome) {
        return docenteRepository.findByNomeAndCognome(nome, cognome);
    }

    public Docente save(Docente d) {
        return docenteRepository.save(d);
    }

    public void delete(Long id) {
        docenteRepository.deleteById(id);
    }

    // Query custom
    public List<Docente> ordina_by_nome_asc() {
        return docenteRepository.ordina_by_nome_asc();
    }

    public List<Docente> ordina_by_nome_desc() {
        return docenteRepository.ordina_by_nome_desc();
    }

}
