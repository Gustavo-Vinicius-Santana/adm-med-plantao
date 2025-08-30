package br.com.projeto_med.adm_med.service;

import br.com.projeto_med.adm_med.model.Plantao;
import br.com.projeto_med.adm_med.repository.PlantaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantaoService {

    private final PlantaoRepository repository;

    public PlantaoService(PlantaoRepository repository) {
        this.repository = repository;
    }

    // Listar todos os plantões
    public List<Plantao> listarTodos() {
        return repository.findAll();
    }

    // Buscar plantão por ID
    public Optional<Plantao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Salvar ou atualizar um plantão
    public Plantao salvar(Plantao plantao) {
        return repository.save(plantao);
    }

    // Deletar plantão por ID
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // Consultas personalizadas (opcional)
    // List<Plantao> findByAlunoId(Long alunoId) { ... }
    // List<Plantao> findByLocalId(Long localId) { ... }
}
