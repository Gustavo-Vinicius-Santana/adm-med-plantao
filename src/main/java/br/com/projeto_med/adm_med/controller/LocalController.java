package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.model.Local;
import br.com.projeto_med.adm_med.service.LocalService;
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
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo local
    @PostMapping
    public ResponseEntity<Local> criar(@RequestBody Local local) {
        Local salvo = service.salvar(local);
        return ResponseEntity.ok(salvo);
    }

    // Atualizar um local existente
    @PutMapping("/{id}")
    public ResponseEntity<Local> atualizar(@PathVariable Long id, @RequestBody Local local) {
        return service.buscarPorId(id)
                .map(localExistente -> {
                    local.setId(id);
                    Local atualizado = service.salvar(local);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar um local
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(localExistente -> {
                    service.deletar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
