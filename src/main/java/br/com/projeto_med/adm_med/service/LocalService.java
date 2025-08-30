package br.com.projeto_med.adm_med.service;

import br.com.projeto_med.adm_med.model.Local;
import br.com.projeto_med.adm_med.repository.LocalRepository;
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

    // Salvar ou atualizar um local
    public Local salvar(Local local) {
        return repository.save(local);
    }

    // Deletar local por ID
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // Exemplo de consulta personalizada (opcional)
    // public List<Local> buscarPorNome(String nome) {
    //     return repository.findByNomeContaining(nome);
    // }
}
