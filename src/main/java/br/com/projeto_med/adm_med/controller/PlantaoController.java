package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.dto.PlantaoDTO;
import br.com.projeto_med.adm_med.dto.PlantaoResponseDTO;
import br.com.projeto_med.adm_med.model.Plantao;
import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.model.Local;
import br.com.projeto_med.adm_med.service.PlantaoService;
import br.com.projeto_med.adm_med.service.UsuarioService;
import br.com.projeto_med.adm_med.service.LocalService;
import br.com.projeto_med.adm_med.exception.ResourceNotFoundException;
import br.com.projeto_med.adm_med.exception.BusinessException;
import org.springframework.http.HttpStatus;
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
        Plantao plantao = plantaoService.buscarPorIdOuFalhar(id);
        return ResponseEntity.ok(toResponseDTO(plantao));
    }

    // Criar um novo plantão
    @PostMapping
    public ResponseEntity<PlantaoResponseDTO> criar(@RequestBody PlantaoDTO dto) {
        // Validações iniciais
        if (dto.nome == null || dto.nome.trim().isEmpty()) {
            throw new BusinessException("O nome do plantão é obrigatório");
        }

        if (dto.horas <= 0) {
            throw new BusinessException("As horas do plantão devem ser maiores que zero");
        }

        // Validação do novo campo dataPlantao
        if (dto.dataPlantao == null) {
            throw new BusinessException("A data do plantão é obrigatória");
        }

        Usuario aluno = usuarioService.buscarPorId(dto.aluno)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "id", dto.aluno));

        Local local = localService.buscarPorId(dto.local)
                .orElseThrow(() -> new ResourceNotFoundException("Local", "id", dto.local));

        // Verificar se o usuário é realmente um aluno
        if (!aluno.getTipo().equals(Usuario.TipoUsuario.ALUNO)) {
            throw new BusinessException("O usuário deve ser um aluno para ser vinculado a um plantão");
        }

        Plantao plantao = new Plantao();
        plantao.setNome(dto.nome);
        plantao.setAluno(aluno);
        plantao.setLocal(local);
        plantao.setHoras(dto.horas);
        plantao.setTurno(dto.turno);
        plantao.setDataPlantao(dto.dataPlantao); // NOVO CAMPO
        plantao.setSemestre(dto.semestre);       // NOVO CAMPO (opcional)

        Plantao salvo = plantaoService.salvar(plantao);

        return new ResponseEntity<>(toResponseDTO(salvo), HttpStatus.CREATED);
    }

    // Atualizar um plantão existente
    @PutMapping("/{id}")
    public ResponseEntity<PlantaoResponseDTO> atualizar(@PathVariable Long id, @RequestBody PlantaoDTO dto) {
        // Validações iniciais
        if (dto.nome == null || dto.nome.trim().isEmpty()) {
            throw new BusinessException("O nome do plantão é obrigatório");
        }

        // if (dto.horas <= 0) {
        //    throw new BusinessException("As horas do plantão devem ser maiores que zero");
        // }

        Plantao plantaoExistente = plantaoService.buscarPorIdOuFalhar(id);

        Usuario aluno = usuarioService.buscarPorId(dto.aluno)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "id", dto.aluno));

        Local local = localService.buscarPorId(dto.local)
                .orElseThrow(() -> new ResourceNotFoundException("Local", "id", dto.local));

        // Verificar se o usuário é realmente um aluno
        if (!aluno.getTipo().equals(Usuario.TipoUsuario.ALUNO)) {
            throw new BusinessException("O usuário deve ser um aluno para ser vinculado a um plantão");
        }

        plantaoExistente.setNome(dto.nome);
        plantaoExistente.setAluno(aluno);
        plantaoExistente.setLocal(local);
        plantaoExistente.setHoras(dto.horas);
        plantaoExistente.setTurno(dto.turno);

        Plantao atualizado = plantaoService.salvar(plantaoExistente);

        return ResponseEntity.ok(toResponseDTO(atualizado));
    }

    // Deletar um plantão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Plantao plantaoExistente = plantaoService.buscarPorIdOuFalhar(id);
        plantaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para buscar plantões por aluno
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<PlantaoResponseDTO>> buscarPorAluno(@PathVariable Long alunoId) {
        // Verificar se o aluno existe
        usuarioService.buscarPorIdOuFalhar(alunoId);

        List<PlantaoResponseDTO> plantoesDTO = plantaoService.findByAlunoId(alunoId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(plantoesDTO);
    }

    // Converte Plantao para DTO de resposta
    private PlantaoResponseDTO toResponseDTO(Plantao plantao) {
        return new PlantaoResponseDTO(
                plantao.getId(),
                plantao.getNome(),
                plantao.getAluno() != null ? plantao.getAluno().getId() : null,
                plantao.getLocal() != null ? plantao.getLocal().getId() : null,
                plantao.getHoras(),
                plantao.getTurno(),
                plantao.getDataPlantao(),  // NOVO CAMPO
                plantao.getSemestre()      // NOVO CAMPO
        );
    }
}
