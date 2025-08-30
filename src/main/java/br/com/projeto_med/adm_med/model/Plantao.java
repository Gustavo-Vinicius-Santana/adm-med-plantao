package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;

@Entity
@Table(name = "plantoes")
public class Plantao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    // Relacionamento com Usuario (aluno)
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Usuario aluno;

    // Relacionamento com Local
    @ManyToOne
    @JoinColumn(name = "id_local", nullable = false)
    private Local local;

    @Column(length = 20)
    private String horas;

    @Column(length = 20)
    private String turno;

    // Construtor vazio
    public Plantao() {}

    // Construtor completo
    public Plantao(String nome, Usuario aluno, Local local, String horas, String turno) {
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

    public String getHoras() {
        return horas;
    }
    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getTurno() {
        return turno;
    }
    public void setTurno(String turno) {
        this.turno = turno;
    }
}
