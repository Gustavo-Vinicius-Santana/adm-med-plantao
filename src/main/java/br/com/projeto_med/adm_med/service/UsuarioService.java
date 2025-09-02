package br.com.projeto_med.adm_med.service;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.repository.UsuarioRepository;
import br.com.projeto_med.adm_med.exception.ResourceNotFoundException;
import br.com.projeto_med.adm_med.exception.BusinessException;
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

    // Buscar por ID ou lançar exceção
    public Usuario buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));
    }

    // Criar ou atualizar
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    // Deletar por ID
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário", "id", id);
        }
        repository.deleteById(id);
    }

    // Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email); // Se usar Optional no repository
    }

    // Buscar por email ou lançar exceção
    public Usuario buscarPorEmailOuFalhar(String email) {
        return buscarPorEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "email", email));
    }

    // Verificar se email existe
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
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
            Usuario usuario = (Usuario) principal;
            return repository.findById(usuario.getId());
        }

        return Optional.empty();
    }

    public Usuario getUsuarioLogadoOuFalhar() {
        return getUsuarioLogado()
                .orElseThrow(() -> new BusinessException("Usuário não autenticado"));
    }

    public Usuario editarUsuario(Long id, Usuario novosDados) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));

        // Quem está logado
        Usuario usuarioLogado = getUsuarioLogadoOuFalhar();

        // Regra para alterar role
        if (novosDados.getTipo() != null && !novosDados.getTipo().equals(usuarioExistente.getTipo())) {
            if (!usuarioLogado.getTipo().equals(Usuario.TipoUsuario.COORDENADOR)) {
                // coordenador pode alterar roles
                throw new BusinessException("Você não tem permissão para alterar a role");
            }

            // Coordenador só pode mudar para ALUNO ou PROFESSOR
            if (novosDados.getTipo().equals(Usuario.TipoUsuario.COORDENADOR)) {
                throw new BusinessException("Não é permitido conceder role de COORDENADOR");
            }

            usuarioExistente.setTipo(novosDados.getTipo());
        }

        // Atualizar outros campos (nome, email, etc.)
        if (novosDados.getNome() != null) {
            usuarioExistente.setNome(novosDados.getNome());
        }

        if (novosDados.getEmail() != null) {
            // Verificar se o novo email já existe em outro usuário
            if (!novosDados.getEmail().equals(usuarioExistente.getEmail())) {
                // Use existsByEmailAndIdNot se estiver disponível
                if (repository.existsByEmailAndIdNot(novosDados.getEmail(), id)) {
                    throw new BusinessException("Email já está em uso: " + novosDados.getEmail());
                }
            }
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

    // Método para verificar permissões
    public boolean usuarioPodeEditar(Long targetUserId) {
        Usuario usuarioLogado = getUsuarioLogadoOuFalhar();

        return usuarioLogado.getTipo().equals(Usuario.TipoUsuario.COORDENADOR) ||
                usuarioLogado.getId().equals(targetUserId);
    }

    public boolean usuarioPodeDeletar(Long targetUserId) {
        Usuario usuarioLogado = getUsuarioLogadoOuFalhar();

        // Coordenador pode deletar qualquer um, exceto a si mesmo
        if (usuarioLogado.getTipo().equals(Usuario.TipoUsuario.COORDENADOR)) {
            return !usuarioLogado.getId().equals(targetUserId);
        }

        // Usuário comum só pode deletar a si mesmo
        return usuarioLogado.getId().equals(targetUserId);
    }
}