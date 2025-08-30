package br.com.projeto_med.adm_med.service;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // Listar todos
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Criar ou atualizar
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    // Deletar por ID
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }
}
