package br.com.compassouol.desafio.domain;

import java.util.Arrays;
import java.util.Optional;

public enum Sexo {

  FEMININO("Feminino"),
  MASCULINO("Masculino"),
  OUTROS("Outros");

  private String descricao;

  Sexo(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  public static Sexo porDescricao(String descricao) {
    final Optional<Sexo> optionalSexo = Arrays.stream(Sexo.values())
        .filter(s -> s.getDescricao().equalsIgnoreCase(descricao)).findFirst();

    return optionalSexo.isPresent() ? optionalSexo.get() : null;
  }
}
