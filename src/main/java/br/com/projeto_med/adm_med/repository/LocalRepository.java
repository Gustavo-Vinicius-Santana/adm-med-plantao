package br.com.projeto_med.adm_med.repository;

import br.com.projeto_med.adm_med.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    List<Local> findByNomeContaining(String nome);
}