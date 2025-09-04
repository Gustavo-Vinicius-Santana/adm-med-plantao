package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "plantoes")
public class Plantao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do plantão é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    // Relacionamento com Usuario (aluno)
    @NotNull(message = "O aluno é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Usuario aluno;

    // Relacionamento com Local
    @NotNull(message = "O local é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_local", nullable = false)
    private Local local;

    @NotNull(message = "As horas do plantão são obrigatórias")
    @DecimalMin(value = "0.0", inclusive = false, message = "As horas do plantão devem ser maiores que zero")
    @Column(nullable = false)
    private Double horas;

    @NotBlank(message = "O turno do plantão é obrigatório")
    @Column(nullable = false, length = 20)
    private String turno;

    // NOVO CAMPO: Data do plantão
    @NotNull(message = "A data do plantão é obrigatória")
    @Column(name = "data_plantao", nullable = false)
    private LocalDate dataPlantao;

    // NOVO CAMPO: Semestre como string (opcional)
    @Size(max = 50, message = "O semestre deve ter no máximo 50 caracteres")
    @Column(length = 50)
    private String semestre;

    // Construtor vazio
    public Plantao() {}

    // Construtor completo atualizado
    public Plantao(String nome, Usuario aluno, Local local, Double horas, String turno, LocalDate dataPlantao, String semestre) {
        this.nome = nome;
        this.aluno = aluno;
        this.local = local;
        this.horas = horas;
        this.turno = turno;
        this.dataPlantao = dataPlantao;
        this.semestre = semestre;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getAluno() {
        return aluno;
    }
    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Local getLocal() {
        return local;
    }
    public void setLocal(Local local) {
        this.local = local;
    }

    public Double getHoras() {
        return horas;
    }
    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public String getTurno() {
        return turno;
    }
    public void setTurno(String turno) {
        this.turno = turno;
    }

    // Novos getters e setters para data e semestre
    public LocalDate getDataPlantao() {
        return dataPlantao;
    }
    public void setDataPlantao(LocalDate dataPlantao) {
        this.dataPlantao = dataPlantao;
    }

    public String getSemestre() {
        return semestre;
    }
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}