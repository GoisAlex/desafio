package br.com.compassouol.desafio.service;

import br.com.compassouol.desafio.domain.Cidade;
import br.com.compassouol.desafio.domain.Estado;
import br.com.compassouol.desafio.domain.dto.CidadeDTO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CidadeServiceTests {

  @Autowired
  private CidadeService cidadeService;

  @Test
  public void deveRetornarConsultaCidadePorNome() {

    final List<CidadeDTO> cidades = cidadeService.buscarPorNome("CuIaBá");

    Assertions.assertEquals(1, cidades.size());
  }

  @Test
  public void deveRetornarConsultaCidadePorEstado() {

    final List<CidadeDTO> cidades = cidadeService.buscarPorEstado(Estado.MATO_GROSSO);

    Assertions.assertNotNull(cidades);
  }

  @Test
  public void deveCadastrarCidade() {

    Cidade novaCidade = new Cidade("São Paulo", Estado.SAO_PAULO);

    final CidadeDTO retorno = cidadeService.cadastrar(CidadeDTO.toDto(novaCidade));

    Assertions.assertNotNull(retorno);
  }

}
