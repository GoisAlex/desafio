package br.com.compassouol.desafio.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

  private String idCliente;

  public ClienteNaoEncontradoException(String idCliente) {
    super();
    this.idCliente = idCliente;
  }

  @Override
  public String getMessage() {
    return String.format("Cliente com id: %s, não encontrado.", idCliente);
  }
}
