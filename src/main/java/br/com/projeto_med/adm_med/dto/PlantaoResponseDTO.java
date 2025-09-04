package br.com.projeto_med.adm_med.dto;

import java.time.LocalDate;

public class PlantaoResponseDTO {
    public Long id;
    public String nome;
    public Long aluno; // ID do aluno
    public Long local; // ID do local
    public Double horas;
    public String turno;
    public LocalDate dataPlantao; // NOVO CAMPO
    public String semestre; // NOVO CAMPO

    // Construtor vazio
    public PlantaoResponseDTO() {}

    // Construtor com todos os campos
    public PlantaoResponseDTO(Long id, String nome, Long aluno, Long local, Double horas,
                              String turno, LocalDate dataPlantao, String semestre) {
        this.id = id;
        this.nome = nome;
        this.aluno = aluno;
        this.local = local;
        this.horas = horas;
        this.turno = turno;
        this.dataPlantao = dataPlantao;
        this.semestre = semestre;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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