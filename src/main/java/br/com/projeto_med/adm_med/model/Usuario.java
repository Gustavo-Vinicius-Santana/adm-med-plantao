package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    public enum TipoUsuario {
        COORDENADOR,
        PROFESSOR,
        ALUNO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Column(nullable = false)
    private String senha;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(length = 20)
    private String telefone;

    @Size(max = 100, message = "Especialização deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String especializacao;

    @Min(value = 0, message = "Horas a fazer não pode ser negativo")
    @Column(name = "horas_a_fazer")
    private Integer horasAFazer;

    @Min(value = 0, message = "Horas feitas não pode ser negativo")
    @Column(name = "horas_feitas")
    private Integer horasFeitas;

    @NotNull(message = "O tipo de usuário é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo;

    // NOVO CAMPO: semestre como string opcional
    @Size(max = 50, message = "O semestre deve ter no máximo 50 caracteres")
    @Column(length = 50)
    private String semestre;

    // Construtor vazio (necessário para JPA)
    public Usuario() {}

    // Construtor completo
    public Usuario(String nome, String email, String telefone,
                   String especializacao, Integer horasAFazer,
                   Integer horasFeitas, TipoUsuario tipo, String semestre) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.especializacao = especializacao;
        this.horasAFazer = horasAFazer;
        this.horasFeitas = horasFeitas;
        this.tipo = tipo;
        this.semestre = semestre;
    }

    // Métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.tipo.name();
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
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

    public Integer getHorasAFazer() {
        return horasAFazer;
    }
    public void setHorasAFazer(Integer horasAFazer) {
        this.horasAFazer = horasAFazer;
    }

    public Integer getHorasFeitas() {
        return horasFeitas;
    }
    public void setHorasFeitas(Integer horasFeitas) {
        this.horasFeitas = horasFeitas;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    // Getter e Setter para o novo campo semestre
    public String getSemestre() {
        return semestre;
    }
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    // Metodo para calcular horas restantes
    public Integer getHorasRestantes() {
        if (horasAFazer == null || horasFeitas == null) {
            return null;
        }
        return Math.max(0, horasAFazer - horasFeitas);
    }

    // Metodo para adicionar horas feitas
    public void adicionarHorasFeitas(Integer horas) {
        if (horas != null && horas > 0) {
            if (this.horasFeitas == null) {
                this.horasFeitas = 0;
            }
            this.horasFeitas += horas;
        }
    }
}