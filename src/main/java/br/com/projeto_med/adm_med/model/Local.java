package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;

@Entity
@Table(name = "locais")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 20)
    private String cep;

    // Especialidade como string
    @Column(length = 500)
    private String especialidade;

    @Column(length = 10)
    private String vagas_manha;

    @Column(length = 10)
    private String vagas_tarde;

    @Column(length = 10)
    private String vagas_noite;

    @Column(length = 10)
    private String vagas_integrais;

    // Construtor vazio
    public Local() {}

    // Construtor completo
    public Local(String nome, String cep, String especialidade,
                 String vagas_manha, String vagas_tarde, String vagas_noite, String vagas_integrais) {
        this.nome = nome;
        this.cep = cep;
        this.especialidade = especialidade;
        this.vagas_manha = vagas_manha;
        this.vagas_tarde = vagas_tarde;
        this.vagas_noite = vagas_noite;
        this.vagas_integrais = vagas_integrais;
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

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getVagas_manha() {
        return vagas_manha;
    }
    public void setVagas_manha(String vagas_manha) {
        this.vagas_manha = vagas_manha;
    }

    public String getVagas_tarde() {
        return vagas_tarde;
    }
    public void setVagas_tarde(String vagas_tarde) {
        this.vagas_tarde = vagas_tarde;
    }

    public String getVagas_noite() {
        return vagas_noite;
    }
    public void setVagas_noite(String vagas_noite) {
        this.vagas_noite = vagas_noite;
    }

    public String getVagas_integrais() {
        return vagas_integrais;
    }
    public void setVagas_integrais(String vagas_integrais) {
        this.vagas_integrais = vagas_integrais;
    }
}