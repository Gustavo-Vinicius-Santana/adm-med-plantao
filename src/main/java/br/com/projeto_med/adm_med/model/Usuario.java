package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    public enum TipoUsuario {
        COORDENADOR,
        PROFESSOR,
        ALUNO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String especializacao;

    @Column(nullable = true)
    private String horas_a_fazer;

    @Column(nullable = true)
    private String horas_feitas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo;

    // Construtor vazio (necessário para JPA)
    public Usuario() {}

    // Construtor completo (opcional, pode facilitar em testes)
    public Usuario(String nome, String email, String telefone,
                   String especializacao, String horas_a_fazer,
                   String horas_feitas, TipoUsuario tipo) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.especializacao = especializacao;
        this.horas_a_fazer = horas_a_fazer;
        this.horas_feitas = horas_feitas;
        this.tipo = tipo;
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEspecializacao() {
        return especializacao;
    }
    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String getHoras_a_fazer() {
        return horas_a_fazer;
    }
    public void setHoras_a_fazer(String horas_a_fazer) {
        this.horas_a_fazer = horas_a_fazer;
    }

    public String getHoras_feitas() {
        return horas_feitas;
    }
    public void setHoras_feitas(String horas_feitas) {
        this.horas_feitas = horas_feitas;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}