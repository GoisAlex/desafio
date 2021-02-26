package br.com.compassouol.desafio.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.compassouol.desafio.domain.Cidade;
import br.com.compassouol.desafio.domain.Estado;
import br.com.compassouol.desafio.domain.dto.CidadeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
public class CidadeResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void deveRetornar201QuandoCadastrarCidade() throws Exception {

    Cidade cidade = new Cidade("Recife", Estado.PERNAMBUCO);

    mockMvc.perform(post("/cidades")
        .content(new ObjectMapper().writeValueAsString(CidadeDTO.toDto(cidade)))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(print());
  }


  @Test
  void deveRetornar200QuandoConsultarCidadePorNome() throws Exception {

    mockMvc.perform(get("/cidades/Cuiabá")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
  }

  @Test
  void deveRetornar404QuandoConsultarCidadePorNomeInexistente() throws Exception {

    mockMvc.perform(get("/cidades/XXX")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void deveRetornar200QuandoConsultarCidadePorNomeDoEstado() throws Exception {

    mockMvc.perform(get("/cidades/estado/MAto GrOssO")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andDo(print());
  }

  @Test
  void deveRetornar200QuandoConsultarCidadePorSiglaDoEstado() throws Exception {

    mockMvc.perform(get("/cidades/estado/SP")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
  }

  @Test
  public void deveGerarExceptionAoConsultarCidadePorEstadoInexistente() throws Exception {

    mockMvc.perform(get("/cidades/estado/MAT")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(r -> assertTrue(
            r.getResolvedException() instanceof br.com.compassouol.desafio.exception.EstadoNaoEncontradoException))
        .andDo(print());
  }


}
