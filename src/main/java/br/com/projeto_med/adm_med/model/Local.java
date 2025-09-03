package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "locais")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do local é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve estar no formato 00000-000 ou 00000000")
    @Column(nullable = false, length = 20)
    private String cep;

    // Especialidade como string
    @Size(max = 500, message = "A especialidade deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String especialidade;

    @Min(value = 0, message = "Vagas da manhã não pode ser negativo")
    @Column(name = "vagas_manha")
    private Integer vagasManha;

    @Min(value = 0, message = "Vagas da tarde não pode ser negativo")
    @Column(name = "vagas_tarde")
    private Integer vagasTarde;

    @Min(value = 0, message = "Vagas da noite não pode ser negativo")
    @Column(name = "vagas_noite")
    private Integer vagasNoite;

    @Min(value = 0, message = "Vagas integrais não pode ser negativo")
    @Column(name = "vagas_integrais")
    private Integer vagasIntegrais;

    // Construtor vazio
    public Local() {
    }

    // Construtor completo
    public Local(String nome, String cep, String especialidade,
                 Integer vagasManha, Integer vagasTarde, Integer vagasNoite, Integer vagasIntegrais) {
        this.nome = nome;
        this.cep = cep;
        this.especialidade = especialidade;
        this.vagasManha = vagasManha;
        this.vagasTarde = vagasTarde;
        this.vagasNoite = vagasNoite;
        this.vagasIntegrais = vagasIntegrais;
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

    public Integer getVagasManha() {
        return vagasManha;
    }

    public void setVagasManha(Integer vagasManha) {
        this.vagasManha = vagasManha;
    }

    public Integer getVagasTarde() {
        return vagasTarde;
    }

    public void setVagasTarde(Integer vagasTarde) {
        this.vagasTarde = vagasTarde;
    }

    public Integer getVagasNoite() {
        return vagasNoite;
    }

    public void setVagasNoite(Integer vagasNoite) {
        this.vagasNoite = vagasNoite;
    }

    public Integer getVagasIntegrais() {
        return vagasIntegrais;
    }

    public void setVagasIntegrais(Integer vagasIntegrais) {
        this.vagasIntegrais = vagasIntegrais;
    }
}