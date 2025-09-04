package br.com.projeto_med.adm_med.repository;

import br.com.projeto_med.adm_med.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Métodos básicos
    boolean existsByEmail(String email);
    boolean existsByTipo(Usuario.TipoUsuario tipo);
    Optional<Usuario> findByEmail(String email);

    // Buscar todos por tipo
    List<Usuario> findByTipo(Usuario.TipoUsuario tipo);

    // Buscar por nome contendo (case insensitive)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // Contar usuários por tipo
    long countByTipo(Usuario.TipoUsuario tipo);

    // Verificar se existe usuário com email E que não seja o ID específico usado na validação
    boolean existsByEmailAndIdNot(String email, Long id);
}