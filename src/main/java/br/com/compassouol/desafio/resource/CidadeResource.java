package br.com.compassouol.desafio.resource;

import br.com.compassouol.desafio.domain.dto.CidadeDTO;
import br.com.compassouol.desafio.exception.EstadoNaoEncontradoException;
import br.com.compassouol.desafio.service.CidadeService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

  @Autowired
  private CidadeService cidadeService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CidadeDTO> cadastrar(@RequestBody CidadeDTO cidade)
      throws URISyntaxException {
    final CidadeDTO cadastrar = cidadeService.cadastrar(cidade);
    return ResponseEntity.created(new URI("/cidades/" + cadastrar.getId())).body(cadastrar);
  }

  @GetMapping(value = "/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CidadeDTO>> porNome(@PathVariable String nome) {
    final List<CidadeDTO> cidades = cidadeService.buscarPorNome(nome);

    return cidades.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cidades);
  }

  @GetMapping(value = "/estado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CidadeDTO>> porEstado(@PathVariable String estado)
      throws EstadoNaoEncontradoException {

    List<CidadeDTO> cidades = cidadeService.buscarPorEstado(cidadeService.getEstado(estado));
    return cidades.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cidades);
  }

}
