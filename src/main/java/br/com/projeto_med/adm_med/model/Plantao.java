package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;

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
    private Double horas; // Alterado de String para Double

    @NotBlank(message = "O turno do plantão é obrigatório")
    @Column(nullable = false, length = 20)
    private String turno;

    // Construtor vazio
    public Plantao() {}

    // Construtor completo
    public Plantao(String nome, Usuario aluno, Local local, Double horas, String turno) {
        this.nome = nome;
        this.aluno = aluno;
        this.local = local;
        this.horas = horas;
        this.turno = turno;
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
}