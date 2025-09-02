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

    public Usuario editarUsuario(Long id, Usuario novosDados) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Quem está logado
        Usuario usuarioLogado = getUsuarioLogado()
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado"));

        // ----- Regra para alterar a role -----
        if (novosDados.getTipo() != null && !novosDados.getTipo().equals(usuarioExistente.getTipo())) {
            if (usuarioLogado.getTipo() != Usuario.TipoUsuario.COORDENADOR) {
                // ❌ Apenas coordenador pode alterar roles
                throw new RuntimeException("Você não tem permissão para alterar a role");
            }

            // Coordenador só pode mudar para ALUNO ou PROFESSOR
            if (novosDados.getTipo() == Usuario.TipoUsuario.COORDENADOR) {
                throw new RuntimeException("Não é permitido conceder role de COORDENADOR");
            }

            usuarioExistente.setTipo(novosDados.getTipo());
        }

        // Atualizar outros campos (nome, email, etc.)
        if (novosDados.getNome() != null) {
            usuarioExistente.setNome(novosDados.getNome());
        }
        if (novosDados.getEmail() != null) {
            usuarioExistente.setEmail(novosDados.getEmail());
        }
        // Se quiser permitir atualizar senha
        if (novosDados.getSenha() != null && !novosDados.getSenha().isBlank()) {
            usuarioExistente.setSenha(novosDados.getSenha()); // lembre de encodar!
        }

        return repository.save(usuarioExistente);
    }

    public boolean existePorTipo(Usuario.TipoUsuario tipo) {
        return repository.existsByTipo(tipo);
    }
}