package br.com.compassouol.desafio.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

  private LocalDateTime data;
  private String mensagem;
  private List<String> erros;
  private String detalhes;

  public static class Builder {

    private LocalDateTime data;
    private String mensagem;
    private List<String> erros;
    private String detalhes;

    public Builder data(LocalDateTime data) {
      this.data = data;
      return this;
    }

    public Builder mensagem(String mensagem) {
      this.mensagem = mensagem;
      return this;
    }

    public Builder erros(List<String> erros) {
      this.erros = erros;
      return this;
    }

    public Builder detalhes(String detalhes) {
      this.detalhes = detalhes;
      return this;
    }

    public ApiError build() {
      return new ApiError(this);
    }
  }

  private ApiError(Builder builder) {
    this.data = builder.data;
    this.detalhes = builder.detalhes;
    this.erros = builder.erros;
    this.mensagem = builder.mensagem;
  }

}
