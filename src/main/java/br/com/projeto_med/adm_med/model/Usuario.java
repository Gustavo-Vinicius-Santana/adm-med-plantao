package br.com.projeto_med.adm_med.model;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private String senha;

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

    // Métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte o TipoUsuario para uma autoridade Spring Security
        // Ex: "COORDENADOR" -> "ROLE_COORDENADOR"
        String role = "ROLE_" + this.tipo.name();
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; // Usamos o email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta nunca é bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // Usuário sempre está habilitado
    }

    // Getters e Setters (mantidos da versão original)
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