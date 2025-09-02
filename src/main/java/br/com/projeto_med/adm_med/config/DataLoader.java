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
            // verifica se já existe algum coordenador
            boolean existeAdmin = usuarioService.existePorTipo(Usuario.TipoUsuario.COORDENADOR);

            if (!existeAdmin) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail("admin@meuapp.com"); // pode ser alterado depois
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setTipo(Usuario.TipoUsuario.COORDENADOR);
                usuarioService.salvar(admin);
                System.out.println("Usuário admin criado com sucesso!");
            } else {
                System.out.println("Já existe um usuário coordenador (admin). Nenhum novo foi criado.");
            }
        };
    }
}