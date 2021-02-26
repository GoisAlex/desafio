package br.com.compassouol.desafio.exception;

public class EstadoNaoEncontradoException extends RuntimeException {

  private String estado;

  public EstadoNaoEncontradoException(String estado) {
    super();
    this.estado = estado;
  }

  @Override
  public String getMessage() {
    return String.format("Estado informado: %s, não encontrado.", estado);
  }
}
