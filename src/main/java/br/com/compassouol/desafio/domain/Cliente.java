package br.com.compassouol.desafio.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = Cliente.TABELA_NOME)
@SequenceGenerator(name = "cliente_sequence", sequenceName = "cliente_sequence", allocationSize = 1)
public class Cliente {

  public static final String TABELA_NOME = "cliente";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_sequence")
  @Column(name = "cliente_id")
  private Integer id;

  @Column(name = "nome_completo", length = 255)
  private String nomeCompleto;

  @Enumerated(value = EnumType.STRING)

  @Column(name = "sexo", length = 10)
  private Sexo sexo;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  @Column(columnDefinition = "date", name = "data_nascimento")
  private LocalDate dataNascimento;

  @Column(name = "idade", length = 3)
  private int idade;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cidade_id", referencedColumnName = "cidade_id")
  private Cidade cidade;

  public Cliente() {
  }

  public Cliente(String nomeCompleto,
      Sexo sexo, LocalDate dataNascimento, int idade,
      Cidade cidade) {
    this.nomeCompleto = nomeCompleto;
    this.sexo = sexo;
    this.dataNascimento = dataNascimento;
    this.idade = idade;
    this.cidade = cidade;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNomeCompleto() {
    return nomeCompleto;
  }

  public void setNomeCompleto(String nomeCompleto) {
    this.nomeCompleto = nomeCompleto;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public void setSexo(Sexo sexo) {
    this.sexo = sexo;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public int getIdade() {
    return idade;
  }

  public void setIdade(int idade) {
    this.idade = idade;
  }

  public Cidade getCidade() {
    return cidade;
  }

  public void setCidade(Cidade cidade) {
    this.cidade = cidade;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cliente cliente = (Cliente) o;
    return id.equals(cliente.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Cliente{" +
        "nomeCompleto='" + nomeCompleto + '\'' +
        '}';
  }
}
