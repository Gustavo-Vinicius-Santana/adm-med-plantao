package br.com.projeto_med.adm_med.service;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

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

    // Implementação exigida pelo Spring Security
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return buscarPorEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

    public Optional<Usuario> getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Usuario) {
            // Agora pegamos diretamente o usuário do contexto de segurança
            Usuario usuario = (Usuario) principal;
            return repository.findById(usuario.getId());
        }

        return Optional.empty();
    }
}