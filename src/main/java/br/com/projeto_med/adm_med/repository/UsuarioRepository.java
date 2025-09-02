package br.com.projeto_med.adm_med.repository;

import br.com.projeto_med.adm_med.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aqui você pode adicionar consultas personalizadas se precisar
    Usuario findByEmail(String email);

    boolean existsByTipo(Usuario.TipoUsuario tipo);
}