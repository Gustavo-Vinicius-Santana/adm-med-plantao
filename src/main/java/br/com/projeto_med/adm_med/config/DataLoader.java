package br.com.projeto_med.adm_med.config;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadAdmin(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@meuapp.com";

            // verifica se já existe
            if (usuarioService.buscarPorEmail(adminEmail).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail(adminEmail);
                admin.setSenha(passwordEncoder.encode("admin123")); // senha criptografada
                admin.setTipo(Usuario.TipoUsuario.COORDENADOR); // ou outro tipo que seja admin
                usuarioService.salvar(admin);
                System.out.println("Usuário admin criado: " + adminEmail);
            } else {
                System.out.println("Usuário admin já existe: " + adminEmail);
            }
        };
    }
}