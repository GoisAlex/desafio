package br.com.compassouol.desafio;

import br.com.compassouol.desafio.repository.CustomRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class DesafioCompassoUolApplication {

  public static void main(String[] args) {
    SpringApplication.run(DesafioCompassoUolApplication.class, args);
  }

}
