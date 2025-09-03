package br.com.projeto_med.adm_med.service;

import br.com.projeto_med.adm_med.model.Local;
import br.com.projeto_med.adm_med.repository.LocalRepository;
import br.com.projeto_med.adm_med.exception.ResourceNotFoundException;
import br.com.projeto_med.adm_med.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

    private final LocalRepository repository;

    public LocalService(LocalRepository repository) {
        this.repository = repository;
    }

    // Listar todos os locais
    public List<Local> listarTodos() {
        return repository.findAll();
    }

    // Buscar local por ID
    public Optional<Local> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Buscar local por ID ou lançar exceção
    public Local buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Local", "id", id));
    }

    // Salvar ou atualizar um local
    public Local salvar(Local local) {
        // Validação adicional: verificar se nome já existe
        if (local.getId() == null && repository.existsByNome(local.getNome())) {
            throw new BusinessException("Já existe um local com o nome: " + local.getNome());
        }

        // Validação para atualização: verificar se nome já existe em outro local
        if (local.getId() != null) {
            Optional<Local> localExistente = repository.findByNome(local.getNome());
            if (localExistente.isPresent() && !localExistente.get().getId().equals(local.getId())) {
                throw new BusinessException("Já existe outro local com o nome: " + local.getNome());
            }
        }

        return repository.save(local);
    }

    // Deletar local por ID
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Local", "id", id);
        }
        repository.deleteById(id);
    }

    // Buscar por nome
    public List<Local> buscarPorNome(String nome) {
        return repository.findByNomeContaining(nome);
    }

    // Verificar se local existe por nome
    public boolean existsByNome(String nome) {
        return repository.existsByNome(nome);
    }
}
