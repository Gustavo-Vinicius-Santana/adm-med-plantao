package br.com.projeto_med.adm_med.repository;

import br.com.projeto_med.adm_med.model.Plantao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantaoRepository extends JpaRepository<Plantao, Long> {

    List<Plantao> findByAlunoId(Long alunoId);
}
