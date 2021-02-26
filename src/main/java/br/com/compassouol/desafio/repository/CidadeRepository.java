package br.com.compassouol.desafio.repository;

import br.com.compassouol.desafio.domain.Cidade;
import br.com.compassouol.desafio.domain.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

  List<Cidade> findByNomeContainingIgnoreCase(String nome);

  List<Cidade> findByEstado(Estado estado);

}
