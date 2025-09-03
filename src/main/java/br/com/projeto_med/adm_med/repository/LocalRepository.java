package br.com.projeto_med.adm_med.repository;

import br.com.projeto_med.adm_med.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    List<Local> findByNomeContaining(String nome);

    Optional<Local> findByNome(String nome);

    boolean existsByNome(String nome);

    // Verificar se existe local com nome E que não seja o ID específico
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Local l WHERE l.nome = :nome AND l.id != :id")
    boolean existsByNomeAndIdNot(String nome, Long id);
}