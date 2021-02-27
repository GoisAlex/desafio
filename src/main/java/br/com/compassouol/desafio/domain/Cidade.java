package br.com.compassouol.desafio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = Cidade.TABELA_NOME)
@SequenceGenerator(name = "cidade_sequence", sequenceName = "cidade_sequence", allocationSize = 1)
public class Cidade {

  public static final String TABELA_NOME = "cidade";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_sequence")
  @Column(name = "cidade_id")
  private Integer id;

  @Column(name = "nome", length = 255)
  private String nome;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "estado", length = 20)
  private Estado estado;

  @Transient
  @JsonIgnore
  @OneToMany(mappedBy = "cidade")
  private List<Cliente> clientes;

  public Cidade() {
  }

  public Cidade(String nome, Estado estado) {
    this.nome = nome;
    this.estado = estado;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cidade cidade = (Cidade) o;
    return id.equals(cidade.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Cidade{" +
        "nome='" + nome + '\'' +
        '}';
  }

}
