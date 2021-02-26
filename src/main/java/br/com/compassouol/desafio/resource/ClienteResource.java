package br.com.compassouol.desafio.resource;

import br.com.compassouol.desafio.domain.dto.ClienteDTO;
import br.com.compassouol.desafio.service.ClienteService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

  @Autowired
  private ClienteService clienteService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ClienteDTO> cadastrar(@RequestBody ClienteDTO cliente)
      throws URISyntaxException {
    final ClienteDTO cadastrar = clienteService.cadastrar(cliente);
    return ResponseEntity.created(new URI("/clientes/" + cadastrar.getId())).body(cadastrar);
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ClienteDTO> atualizar(@RequestBody ClienteDTO cliente) {
    return ResponseEntity.ok().body(clienteService.atualizar(cliente));
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> atualizar(@PathVariable Integer id) {
    clienteService.excluir(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ClienteDTO>> porNome(@PathVariable String nome) {
    final List<ClienteDTO> clientes = clienteService.buscarPorNome(nome);
    return clientes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(clientes);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ClienteDTO> porId(@PathVariable Integer id) {
    return ResponseEntity.ok(clienteService.buscarPorId(id));
  }


}
