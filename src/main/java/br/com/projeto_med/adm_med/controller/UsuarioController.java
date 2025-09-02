package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.service.UsuarioService;
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
    private final PasswordEncoder passwordEncoder; // injetado aqui

    public UsuarioController(UsuarioService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder; // atribuído no construtor
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
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = service.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioLogadoOpt = service.getUsuarioLogado();
        Usuario usuarioAlvo = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica permissões
        if (usuarioLogadoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Usuario usuarioLogado = usuarioLogadoOpt.get();

        if (!usuarioLogado.getTipo().equals(Usuario.TipoUsuario.COORDENADOR) &&
                !usuarioLogado.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Mantém o ID
        usuario.setId(id);

        // Mantém sempre a senha atual
        usuario.setSenha(usuarioAlvo.getSenha());

        Usuario atualizado = service.salvar(usuario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Usuario> usuarioLogado = service.getUsuarioLogado();
        Usuario usuarioAlvo = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Se não for admin e não for o próprio usuário, não permite
        if (!usuarioLogado.get().getTipo().equals(Usuario.TipoUsuario.COORDENADOR) &&
                !usuarioLogado.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}