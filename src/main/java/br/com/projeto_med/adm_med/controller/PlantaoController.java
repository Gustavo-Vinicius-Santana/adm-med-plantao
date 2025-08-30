package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.dto.PlantaoDTO;
import br.com.projeto_med.adm_med.dto.PlantaoResponseDTO;
import br.com.projeto_med.adm_med.model.Plantao;
import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.model.Local;
import br.com.projeto_med.adm_med.service.PlantaoService;
import br.com.projeto_med.adm_med.service.UsuarioService;
import br.com.projeto_med.adm_med.service.LocalService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plantoes")
public class PlantaoController {

    private final PlantaoService plantaoService;
    private final UsuarioService usuarioService;
    private final LocalService localService;

    public PlantaoController(PlantaoService plantaoService, UsuarioService usuarioService, LocalService localService) {
        this.plantaoService = plantaoService;
        this.usuarioService = usuarioService;
        this.localService = localService;
    }

    // Listar todos os plantões (DTO)
    @GetMapping
    public ResponseEntity<List<PlantaoResponseDTO>> listarTodos() {
        List<PlantaoResponseDTO> plantoesDTO = plantaoService.listarTodos()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(plantoesDTO);
    }

    // Buscar plantão por ID (DTO)
    @GetMapping("/{id}")
    public ResponseEntity<PlantaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return plantaoService.buscarPorId(id)
                .map(plantao -> ResponseEntity.ok(toResponseDTO(plantao)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo plantão
    @PostMapping
    public ResponseEntity<PlantaoResponseDTO> criar(@RequestBody PlantaoDTO dto) {

        Usuario aluno = usuarioService.buscarPorId(dto.aluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Local local = localService.buscarPorId(dto.local)
                .orElseThrow(() -> new RuntimeException("Local não encontrado"));

        Plantao plantao = new Plantao();
        plantao.setNome(dto.nome);
        plantao.setAluno(aluno);
        plantao.setLocal(local);
        plantao.setHoras(dto.horas);
        plantao.setTurno(dto.turno);

        Plantao salvo = plantaoService.salvar(plantao);

        return ResponseEntity.ok(toResponseDTO(salvo));
    }

    // Atualizar um plantão existente
    @PutMapping("/{id}")
    public ResponseEntity<PlantaoResponseDTO> atualizar(@PathVariable Long id, @RequestBody PlantaoDTO dto) {

        return plantaoService.buscarPorId(id)
                .map(plantaoExistente -> {

                    Usuario aluno = usuarioService.buscarPorId(dto.aluno)
                            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

                    Local local = localService.buscarPorId(dto.local)
                            .orElseThrow(() -> new RuntimeException("Local não encontrado"));

                    plantaoExistente.setNome(dto.nome);
                    plantaoExistente.setAluno(aluno);
                    plantaoExistente.setLocal(local);
                    plantaoExistente.setHoras(dto.horas);
                    plantaoExistente.setTurno(dto.turno);

                    Plantao atualizado = plantaoService.salvar(plantaoExistente);

                    return ResponseEntity.ok(toResponseDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar um plantão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return plantaoService.buscarPorId(id)
                .map(plantaoExistente -> {
                    plantaoService.deletar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Converte Plantao para DTO de resposta
    private PlantaoResponseDTO toResponseDTO(Plantao plantao) {
        PlantaoResponseDTO dto = new PlantaoResponseDTO();
        dto.id = plantao.getId();
        dto.nome = plantao.getNome();
        dto.aluno = plantao.getAluno().getId();
        dto.local = plantao.getLocal().getId();
        dto.horas = plantao.getHoras();
        dto.turno = plantao.getTurno();
        return dto;
    }
}
