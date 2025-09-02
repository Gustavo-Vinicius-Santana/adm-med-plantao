package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.service.UsuarioService;
import br.com.projeto_med.adm_med.exception.ResourceNotFoundException;
import br.com.projeto_med.adm_med.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = service.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = service.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));
        return ResponseEntity.ok(usuario);
    }

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        // Verifica se email já existe
        if (service.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + usuario.getEmail());
        }

        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = service.salvar(usuario);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    // atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioLogado = service.getUsuarioLogadoOuFalhar(); // ✅ Mudar aqui

        // Só coordenador pode editar outros. Usuário comum só edita a si mesmo
        if (!usuarioLogado.getTipo().equals(Usuario.TipoUsuario.COORDENADOR) &&
                !usuarioLogado.getId().equals(id)) {
            throw new BusinessException("Você não tem permissão para editar este usuário");
        }

        Usuario atualizado = service.editarUsuario(id, usuario);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Usuario usuarioLogado = service.getUsuarioLogadoOuFalhar(); // ✅ Mudar aqui

        Usuario usuarioAlvo = service.buscarPorIdOuFalhar(id); // ✅ Usar método que lança exceção

        // Se não for coordenador e não for o próprio usuário, não permite
        if (!usuarioLogado.getTipo().equals(Usuario.TipoUsuario.COORDENADOR) &&
                !usuarioLogado.getId().equals(id)) {
            throw new BusinessException("Você não tem permissão para deletar este usuário");
        }

        // Se for coordenador tentando deletar a si mesmo, bloqueia
        if (usuarioLogado.getTipo().equals(Usuario.TipoUsuario.COORDENADOR) &&
                usuarioLogado.getId().equals(id)) {
            throw new BusinessException("Coordenadores não podem deletar a própria conta");
        }

        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint adicional para buscar usuário por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = service.buscarPorEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "email", email));
        return ResponseEntity.ok(usuario);
    }
}