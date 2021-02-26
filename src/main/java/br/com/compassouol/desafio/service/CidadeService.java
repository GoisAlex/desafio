package br.com.compassouol.desafio.service;

import br.com.compassouol.desafio.domain.Cidade;
import br.com.compassouol.desafio.domain.Estado;
import br.com.compassouol.desafio.domain.dto.CidadeDTO;
import br.com.compassouol.desafio.exception.EstadoNaoEncontradoException;
import br.com.compassouol.desafio.repository.CidadeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

  @Autowired
  private CidadeRepository cidadeRepository;

  public CidadeDTO cadastrar(CidadeDTO cidade) {
    return CidadeDTO.toDto(cidadeRepository.save(cidade.toObject(cidade)));
  }

  public List<CidadeDTO> buscarPorNome(String nome) {
    return converterParaDTO(cidadeRepository.findByNomeContainingIgnoreCase(nome));
  }

  public List<CidadeDTO> buscarPorEstado(Estado estado) {
    return converterParaDTO(cidadeRepository.findByEstado(estado));
  }

  public Estado getEstado(String valor) throws EstadoNaoEncontradoException {

    final Estado estado = Estado.buscarEstado(valor);

    if (estado == null) {
      throw new EstadoNaoEncontradoException(valor);
    }

    return estado;
  }

  private List<CidadeDTO> converterParaDTO(List<Cidade> cidades) {
    return cidades.stream().map(c -> CidadeDTO.toDto(c)).collect(Collectors.toList());
  }

}
