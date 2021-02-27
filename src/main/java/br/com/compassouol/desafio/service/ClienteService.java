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
    final Cliente save = clienteRepository.save(ClienteDTO.toObject(cliente));
    clienteRepository.refresh(save);
    return ClienteDTO.toDto(save);
  }

  public ClienteDTO atualizar(Integer id, String nome) {

    final Cliente clienteBD = clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNaoEncontradoException(id.toString()));

    clienteBD.setNomeCompleto(nome);

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
