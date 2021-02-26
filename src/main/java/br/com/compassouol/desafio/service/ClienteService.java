package br.com.compassouol.desafio.service;

import br.com.compassouol.desafio.domain.Cliente;
import br.com.compassouol.desafio.domain.dto.ClienteDTO;
import br.com.compassouol.desafio.exception.ClienteNaoEncontradoException;
import br.com.compassouol.desafio.repository.ClienteRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  public ClienteDTO cadastrar(ClienteDTO cliente) {
    return ClienteDTO.toDto(clienteRepository.save(ClienteDTO.toObject(cliente)));
  }

  public ClienteDTO atualizar(ClienteDTO cliente) {

    final Cliente clienteBD = clienteRepository.findById(cliente.getId())
        .orElseThrow(() -> new ClienteNaoEncontradoException(cliente.getId().toString()));

    BeanUtils
        .copyProperties(ClienteDTO.toObject(cliente), clienteBD, "id", "idade", "dataNascimento",
            "sexo", "cidade");

    return ClienteDTO.toDto(clienteRepository.save(clienteBD));
  }

  public List<ClienteDTO> buscarPorNome(String nome) {
    return converterParaDTO(clienteRepository.findByNomeCompletoContainingIgnoreCase(nome));
  }

  public ClienteDTO buscarPorId(Integer id) {
    return ClienteDTO.toDto(clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNaoEncontradoException(id.toString())));
  }

  public void excluir(Integer id) {
    clienteRepository.delete(ClienteDTO.toObject(buscarPorId(id)));
  }

  private List<ClienteDTO> converterParaDTO(List<Cliente> clientes) {
    return clientes.stream().map(c -> ClienteDTO.toDto(c)).collect(Collectors.toList());
  }
}
