package br.com.projeto_med.adm_med.controller;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.security.JwtUtil;
import br.com.projeto_med.adm_med.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

            System.out.println("Tentativa de login: email=" + email + ", senha=" + senha);

            Usuario usuario = usuarioService.buscarPorEmail(email)
                    .orElseThrow(() -> {
                        System.out.println("Usuário não encontrado: " + email);
                        return new RuntimeException("Usuário não encontrado");
                    });

            System.out.println("Senha fornecida: " + senha);
            System.out.println("Senha armazenada: " + usuario.getSenha());

            boolean senhaCorreta = passwordEncoder.matches(senha, usuario.getSenha());
            System.out.println("Senha coincide: " + senhaCorreta);

            if (!senhaCorreta) {
                System.out.println("Senha inválida para usuário: " + email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Credenciais inválidas"));
            }

            String token = jwtUtil.gerarToken(email);
            System.out.println("Login bem-sucedido para: " + email);
            return ResponseEntity.ok(Map.of("token", token, "email", email));

        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Falha na autenticação"));
        }
    }
}