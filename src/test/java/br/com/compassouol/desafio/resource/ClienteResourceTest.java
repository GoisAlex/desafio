package br.com.compassouol.desafio.resource;

import static br.com.compassouol.desafio.domain.Sexo.MASCULINO;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.compassouol.desafio.domain.Cliente;
import br.com.compassouol.desafio.domain.dto.ClienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ClienteResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void deveRetornar201QuandoCadastrarCliente() throws Exception {

    Cliente cliente = new Cliente();
    cliente.setNomeCompleto("ALEX");
    cliente.setIdade(30);
    cliente.setDataNascimento(LocalDate.of(1990, 04, 20));
    cliente.setSexo(MASCULINO);

    mockMvc.perform(post("/clientes")
        .content(new ObjectMapper().writeValueAsString(ClienteDTO.toDto(cliente)))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  void deveRetornar200QuandoAtualizarCliente() throws Exception {

    mockMvc.perform(put("/clientes/999/nome/ALEX UPDATE")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }


  @Test
  @Order(1)
  void deveRetornar200QuandoConsultarClientePorNome() throws Exception {

    mockMvc.perform(get("/clientes/nome/AleX")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
  }

  @Test
  void deveRetornar404QuandoConsultarClientePorNomeInexistente() throws Exception {

    mockMvc.perform(get("/clientes/nome/XXX")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void deveRetornar200QuandoConsultarClientePorId() throws Exception {

    mockMvc.perform(get("/clientes/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void deveRetornar204QuandoExcluirClientePorId() throws Exception {

    mockMvc.perform(delete("/clientes/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  public void deveGerarExceptionAoConsultarClientePorIdInexistente() throws Exception {

    mockMvc.perform(get("/clientes/-1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(r -> assertTrue(
            r.getResolvedException() instanceof br.com.compassouol.desafio.exception.ClienteNaoEncontradoException))
        .andDo(print());
  }

}
