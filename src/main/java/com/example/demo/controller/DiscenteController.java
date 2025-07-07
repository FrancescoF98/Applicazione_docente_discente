package com.example.demo.controller;

import com.example.demo.converter.Converter;
import com.example.demo.data.dto.DiscenteDTO;
import com.example.demo.data.entity.Discente;
import com.example.demo.data.entity.Docente;
import com.example.demo.service.DiscenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/discenti")
public class DiscenteController {

    @Autowired
    DiscenteService discenteService;
    @Autowired
    Converter converter;


    // GET - tutti i discenti
    @GetMapping("/lista")
    public ResponseEntity<List<DiscenteDTO>> list(@RequestParam(name = "filtro", required = false) String filtro) {
        List<Discente> discenti = new ArrayList<>();

        if ("asc".equalsIgnoreCase(filtro)) {
            discenti = discenteService.ordina_by_nome_asc();
        } else if ("desc".equalsIgnoreCase(filtro)){
            discenti = discenteService.ordina_by_nome_desc();
        } else {
            discenti = discenteService.findAll();
        }

        return ResponseEntity.ok(converter.discente_convert_to_dto(discenti));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscenteDTO> getDiscenteById(@PathVariable Long id) {
        Discente discente = discenteService.get(id);
        DiscenteDTO discenteDTO = converter.discente_convert_to_dto(discente);
        if (discente != null) {
            return ResponseEntity.ok(discenteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<DiscenteDTO> getDiscenteByName(@RequestParam String nome, @RequestParam String cognome,  @RequestParam Integer matricola) {
        Discente discente = discenteService.getByNomeAndCognomeAndMatricola(nome, cognome, matricola);
        DiscenteDTO discenteDTO = converter.discente_convert_to_dto(discente);
        if (discente != null) {
            return ResponseEntity.ok(discenteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST - nuovo
    @PostMapping("/new")
    public ResponseEntity<DiscenteDTO> showAdd(@RequestBody DiscenteDTO discente) {

        // converto
        Discente nuovo = converter.discente_convert_to_entity(discente);
        // salvo
        discenteService.save(nuovo);
        //
        return ResponseEntity.ok(discente);

    }


    // PUT - modifica
    @PutMapping("/{id}/edit")
    public ResponseEntity<DiscenteDTO> showEdit(@PathVariable Long id, @RequestBody DiscenteDTO aggiornato) {
        // questa linea non ha senso ma funziona e la lascio così - 2025/05/26
        DiscenteDTO discente = converter.discente_convert_to_dto(discenteService.get(id));

        //
        discente.setNome(aggiornato.getNome());
        discente.setCognome(aggiornato.getCognome());
        discente.setMatricola(aggiornato.getMatricola());
        discente.setEta(aggiornato.getEta());

        Discente modificato = converter.discente_convert_to_entity(discente);

        discenteService.save(modificato);
        return ResponseEntity.ok(discente);
    }


    // DELETE
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        discenteService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

