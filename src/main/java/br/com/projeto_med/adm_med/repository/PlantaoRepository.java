package br.com.projeto_med.adm_med.repository;

import br.com.projeto_med.adm_med.model.Plantao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantaoRepository extends JpaRepository<Plantao, Long> {

    List<Plantao> findByAlunoId(Long alunoId);

    // Adicione outras consultas se necessário
    List<Plantao> findByLocalId(Long localId);

    boolean existsByAlunoId(Long alunoId);

    // Verificar conflitos de horário (exemplo)
    // @Query("SELECT p FROM Plantao p WHERE p.aluno.id = :alunoId AND p.turno = :turno")
    // List<Plantao> findByAlunoAndTurno(Long alunoId, String turno);
}