package br.com.projeto_med.adm_med.dto;

import br.com.projeto_med.adm_med.model.Usuario;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String especializacao;
    private Integer horasAFazer;
    private Integer horasFeitas;
    private Usuario.TipoUsuario tipo;
    private Integer horasRestantes;
    private Collection<? extends GrantedAuthority> authorities;

    // Construtor a partir da entidade Usuario
    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.especializacao = usuario.getEspecializacao();
        this.horasAFazer = usuario.getHorasAFazer();
        this.horasFeitas = usuario.getHorasFeitas();
        this.tipo = usuario.getTipo();
        this.horasRestantes = usuario.getHorasRestantes();
        this.authorities = usuario.getAuthorities();
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEspecializacao() { return especializacao; }
    public Integer getHorasAFazer() { return horasAFazer; }
    public Integer getHorasFeitas() { return horasFeitas; }
    public Usuario.TipoUsuario getTipo() { return tipo; }
    public Integer getHorasRestantes() { return horasRestantes; }
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
}
