package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.security.JwtUtil;
import br.com.projeto_med.adm_med.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String senha = payload.get("senha");

            Usuario usuario = usuarioService.buscarPorEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            if (!passwordEncoder.matches(senha, usuario.getSenha())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Credenciais inválidas"));
            }

            // ✅ Gera o token usando o ID do usuário
            String token = jwtUtil.generateToken(usuario, usuario.getId());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", email,
                    "role", usuario.getTipo().name() // opcional: retorna o tipo de usuário
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Falha na autenticação"));
        }
    }
}