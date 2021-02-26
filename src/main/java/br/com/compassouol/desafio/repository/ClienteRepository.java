package br.com.compassouol.desafio.repository;

import br.com.compassouol.desafio.domain.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

  List<Cliente> findByNomeCompletoContainingIgnoreCase(String nomeCompleto);
}
