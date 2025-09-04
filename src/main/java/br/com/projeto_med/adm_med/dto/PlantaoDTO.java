package br.com.projeto_med.adm_med.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class PlantaoDTO {

    @NotBlank(message = "O nome do plantão é obrigatório")
    public String nome;

    @NotNull(message = "O aluno é obrigatório")
    public Long aluno; // ID do aluno

    @NotNull(message = "O local é obrigatório")
    public Long local; // ID do local

    @NotNull(message = "As horas do plantão são obrigatórias")
    @DecimalMin(value = "0.0", inclusive = false, message = "As horas do plantão devem ser maiores que zero")
    public Double horas;

    @NotBlank(message = "O turno do plantão é obrigatório")
    public String turno;

    // NOVOS CAMPOS
    @NotNull(message = "A data do plantão é obrigatória")
    public LocalDate dataPlantao;

    @Size(max = 50, message = "O semestre deve ter no máximo 50 caracteres")
    public String semestre;

    // Construtor vazio
    public PlantaoDTO() {}

    // Construtor completo
    public PlantaoDTO(String nome, Long aluno, Long local, Double horas, String turno,
                      LocalDate dataPlantao, String semestre) {
        this.nome = nome;
        this.aluno = aluno;
        this.local = local;
        this.horas = horas;
        this.turno = turno;
        this.dataPlantao = dataPlantao;
        this.semestre = semestre;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Long getAluno() { return aluno; }
    public void setAluno(Long aluno) { this.aluno = aluno; }

    public Long getLocal() { return local; }
    public void setLocal(Long local) { this.local = local; }

    public Double getHoras() { return horas; }
    public void setHoras(Double horas) { this.horas = horas; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public LocalDate getDataPlantao() { return dataPlantao; }
    public void setDataPlantao(LocalDate dataPlantao) { this.dataPlantao = dataPlantao; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
}