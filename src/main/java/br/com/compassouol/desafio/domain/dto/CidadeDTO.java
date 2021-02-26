package br.com.compassouol.desafio.domain.dto;

import br.com.compassouol.desafio.domain.Cidade;
import br.com.compassouol.desafio.domain.Estado;
import org.springframework.util.StringUtils;

public class CidadeDTO {

  private Integer id;
  private String nome;
  private String estado;

  public Integer getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getEstado() {
    return estado;
  }

  public static CidadeDTO toDto(Cidade cidade) {
    CidadeDTO dto = new CidadeDTO();

    dto.id = cidade.getId() != null ? cidade.getId() : null;
    dto.nome = cidade.getNome();
    dto.estado = cidade.getEstado() != null ? cidade.getEstado().getNome() : null;

    return dto;
  }

  public static Cidade toObject(CidadeDTO cidadeDTO) {
    Cidade cidade = new Cidade();

    cidade.setId(cidadeDTO.id);
    cidade.setNome(cidadeDTO.nome);

    if (StringUtils.hasText(cidadeDTO.estado)) {
      cidade.setEstado(cidadeDTO.converterEstado(cidadeDTO.estado));
    }

    return cidade;
  }

  public Estado converterEstado(String valor) {
    return Estado.buscarEstado(valor);
  }
}
