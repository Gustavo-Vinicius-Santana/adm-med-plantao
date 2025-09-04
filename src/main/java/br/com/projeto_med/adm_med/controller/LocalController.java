package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.model.Local;
import br.com.projeto_med.adm_med.service.LocalService;
import br.com.projeto_med.adm_med.exception.ResourceNotFoundException;
import br.com.projeto_med.adm_med.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locais")
public class LocalController {

    private final LocalService service;

    public LocalController(LocalService service) {
        this.service = service;
    }

    // Listar todos os locais
    @GetMapping
    public ResponseEntity<List<Local>> listarTodos() {
        List<Local> locais = service.listarTodos();
        return ResponseEntity.ok(locais);
    }

    // Buscar local por ID
    @GetMapping("/{id}")
    public ResponseEntity<Local> buscarPorId(@PathVariable Long id) {
        Local local = service.buscarPorIdOuFalhar(id);
        return ResponseEntity.ok(local);
    }

    // Criar um novo local
    @PostMapping
    public ResponseEntity<Local> criar(@RequestBody Local local) {
        Local salvo = service.salvar(local);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    // Atualizar um local existente
    @PutMapping("/{id}")
    public ResponseEntity<Local> atualizar(@PathVariable Long id, @RequestBody Local local) {
        // Verifica se o local existe
        service.buscarPorIdOuFalhar(id);

        // Atualiza o ID e salva
        local.setId(id);
        Local atualizado = service.salvar(local);

        return ResponseEntity.ok(atualizado);
    }

    // Deletar um local
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Verifica se o local existe
        service.buscarPorIdOuFalhar(id);

        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar locais por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<Local>> buscarPorNome(@RequestParam String nome) {
        List<Local> locais = service.buscarPorNome(nome);
        return ResponseEntity.ok(locais);
    }
}
