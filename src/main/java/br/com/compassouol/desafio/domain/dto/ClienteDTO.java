package br.com.compassouol.desafio.domain.dto;

import br.com.compassouol.desafio.domain.Cidade;
import br.com.compassouol.desafio.domain.Cliente;
import br.com.compassouol.desafio.domain.Estado;
import br.com.compassouol.desafio.domain.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

public class ClienteDTO {

  private Integer id;
  private String nomeCompleto;
  private String sexo;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private String dataNascimento;
  private int idade;
  private CidadeDTO cidade;

  public Integer getId() {
    return id;
  }

  public String getNomeCompleto() {
    return nomeCompleto;
  }

  public String getSexo() {
    return sexo;
  }

  public String getDataNascimento() {
    return dataNascimento;
  }

  public int getIdade() {
    return idade;
  }

  public CidadeDTO getCidade() {
    return cidade;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setNomeCompleto(String nomeCompleto) {
    this.nomeCompleto = nomeCompleto;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public void setDataNascimento(String dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public void setIdade(int idade) {
    this.idade = idade;
  }

  public void setCidade(CidadeDTO cidade) {
    this.cidade = cidade;
  }

  public static ClienteDTO toDto(Cliente cliente) {
    ClienteDTO dto = new ClienteDTO();

    dto.id = cliente.getId() != null ? cliente.getId() : null;
    dto.nomeCompleto = cliente.getNomeCompleto();
    dto.idade = cliente.getIdade();
    dto.sexo = cliente.getSexo() != null ? cliente.getSexo().getDescricao() : null;

    if (cliente.getDataNascimento() != null) {
      dto.dataNascimento = cliente.getDataNascimento().format(DateTimeFormatter.ISO_DATE);
    }

    if (cliente.getCidade() != null) {
      dto.setCidade(CidadeDTO.toDto(cliente.getCidade()));
    }

    return dto;
  }

  public static Cliente toObject(ClienteDTO clienteDTO) {
    Cliente cliente = new Cliente();

    cliente.setId(clienteDTO.id);
    cliente.setNomeCompleto(clienteDTO.nomeCompleto);
    cliente.setSexo(Sexo.porDescricao(clienteDTO.sexo));
    cliente.setIdade(clienteDTO.idade);
    if (StringUtils.hasText(clienteDTO.dataNascimento)) {
      cliente.setDataNascimento(LocalDate.parse(clienteDTO.dataNascimento));
    }

    if (clienteDTO.cidade != null) {
      Cidade cidade = new Cidade();

      cidade.setId(clienteDTO.cidade.getId());
      cidade.setEstado(Estado.buscarEstado(clienteDTO.cidade.getEstado()));
      cidade.setNome(clienteDTO.cidade.getNome());

      cliente.setCidade(cidade);
    }

    return cliente;
  }


}
