package com.example.demo.controller;

import com.example.demo.config.CustomConfig;
import com.example.demo.converter.Converter;
import com.example.demo.data.dto.DocenteDTO;
import com.example.demo.data.entity.Docente;
import com.example.demo.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/docenti")
public class DocenteController {

    @Autowired
    DocenteService docenteService;
    @Autowired
    Converter converter;

    private final CustomConfig config;

    public DocenteController(CustomConfig config) {
        this.config = config;
    }

    @GetMapping("/")
    public String message() {
        return config.getMessage();
    }

    // GET - tutti i docenti
    @GetMapping("/lista")
    public ResponseEntity<List<DocenteDTO>> list(@RequestParam(name = "filtro", required = false) String filtro) {
        List<Docente> docenti = new ArrayList<>();

        if ("asc".equalsIgnoreCase(filtro)) {
            docenti = docenteService.ordina_by_nome_asc();
        } else if ("desc".equalsIgnoreCase(filtro)){
            docenti = docenteService.ordina_by_nome_desc();
        } else {
            docenti = docenteService.findAll();
        }

        return ResponseEntity.ok(converter.docente_convert_to_dto(docenti));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Docente> getDocenteById(@PathVariable Long id) {
//        Docente docente = docenteService.get(id);
//        if (docente != null) {
//            return ResponseEntity.ok(docente);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/{nome}_{cognome}")
//    public ResponseEntity<Docente> getDocenteByName(@PathVariable String nome, @PathVariable String cognome) {
//        Docente docente = docenteService.getByNomeAndCognome(nome, cognome);
//        if (docente != null) {
//            return ResponseEntity.ok(docente);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/search")
    public ResponseEntity<Docente> getDocenteByName(@RequestParam String nome, @RequestParam String cognome) {
        Docente docente = docenteService.getByNomeAndCognome(nome, cognome);
        if (docente != null) {
            return ResponseEntity.ok(docente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // POST - nuovo docente
    @PostMapping("/new")
    public ResponseEntity<DocenteDTO> showAdd(@RequestBody DocenteDTO docente) {
        // converto
        Docente nuovo = converter.docente_convert_to_entity(docente);
        // salvo
        docenteService.save(nuovo);
        //
        return ResponseEntity.ok(docente);
    }

    // PUT - modifica
    @PutMapping("/{id}/edit")
    public ResponseEntity<DocenteDTO> showEdit(@PathVariable Long id, @RequestBody DocenteDTO doc_aggiornato) {
        // questa linea non ha senso ma funziona e la lascio così - 2025/05/26
        DocenteDTO docente = converter.docente_convert_to_dto(docenteService.get(id));

        //
        docente.setNome(doc_aggiornato.getNome());
        docente.setCognome(doc_aggiornato.getCognome());

        Docente modificato = converter.docente_convert_to_entity(docente);

        docenteService.save(modificato);
        return ResponseEntity.ok(docente);

    }


    // DELETE
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

//        List<Corso> corsi = corsoRepository.trova_corsi_con_id_docente(id);
//
//        if (corsi.isEmpty()) {
//            docenteService.delete(id);
//        } else{
//            throw new RuntimeException("Impossibile eliminare il docente: ci sono corsi associati.");
//        }

        docenteService.delete(id);
        return ResponseEntity.noContent().build();
    }




}

