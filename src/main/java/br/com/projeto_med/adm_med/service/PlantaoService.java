package br.com.projeto_med.adm_med.service;

import br.com.projeto_med.adm_med.model.Plantao;
import br.com.projeto_med.adm_med.repository.PlantaoRepository;
import br.com.projeto_med.adm_med.exception.ResourceNotFoundException;
import br.com.projeto_med.adm_med.exception.BusinessException;
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

    // Buscar plantão por ID ou lançar exceção
    public Plantao buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantão", "id", id));
    }

    // Salvar ou atualizar um plantão
    public Plantao salvar(Plantao plantao) {
        if (plantao.getNome() == null || plantao.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome do plantão é obrigatório");
        }

        return repository.save(plantao);
    }

    // Deletar plantão por ID
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Plantão", "id", id);
        }
        repository.deleteById(id);
    }

    // Consultas personalizadas
    public List<Plantao> findByAlunoId(Long alunoId) {
        return repository.findByAlunoId(alunoId);
    }
}
