package br.com.compassouol.desafio.service;

import static br.com.compassouol.desafio.domain.Sexo.MASCULINO;

import br.com.compassouol.desafio.domain.Cliente;
import br.com.compassouol.desafio.domain.dto.ClienteDTO;
import br.com.compassouol.desafio.exception.ClienteNaoEncontradoException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@TestMethodOrder(OrderAnnotation.class)
public class ClienteServiceTests {

  @Autowired
  private ClienteService clienteService;

  @Test
  public void deveCadastrarCliente() {

    final Cliente cliente = new Cliente();

    cliente.setNomeCompleto("ALEX");
    cliente.setIdade(30);
    cliente.setDataNascimento(LocalDate.now());
    cliente.setSexo(MASCULINO);

    final ClienteDTO cadastrar = clienteService.cadastrar(ClienteDTO.toDto(cliente));

    Assertions.assertNotNull(cadastrar);
    Assertions.assertNotNull(cadastrar.getId());

  }

  @Test
  @Order(1)
  public void deveRetornarConsultaClientePorNome() {

    final List<ClienteDTO> retorno = clienteService
        .buscarPorNome("AlEx GoIs");

    Assertions.assertNotNull(retorno);
  }

  @Test
  @Order(2)
  public void deveAtualizarCliente() {

    final ClienteDTO atualizar = clienteService.atualizar(999, "ALEX");

    Assertions.assertNotNull(atualizar);
    Assertions.assertEquals(atualizar.getNomeCompleto(), "ALEX");
  }

  @Test
  @Order(3)
  public void deveRetornarConsultaClientePorId() {

    final ClienteDTO retorno = clienteService.buscarPorId(999);

    Assertions.assertNotNull(retorno);
  }

  @Test
  public void deveGerarExceptionAoConsultarClienteInexistentePorId() {

    Assertions.assertThrows(ClienteNaoEncontradoException.class, () -> {
      clienteService.buscarPorId(6666);
    });
  }

  @Test
  public void deveGerarExceptionAoAtualizarClienteInexistente() {

    Assertions.assertThrows(ClienteNaoEncontradoException.class, () -> {
      clienteService.atualizar(666, "ALEX");
    });
  }

  @Test
  public void deveRemoverCliente() {

    Assertions.assertDoesNotThrow(() -> {
      clienteService.excluir(999);
    });
  }

}
