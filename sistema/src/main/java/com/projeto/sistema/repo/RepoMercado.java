package com.projeto.sistema.repo;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.projeto.sistema.model.Mercado;


@Repository
public interface RepoMercado extends CrudRepository<Mercado, Long> {
  Optional<Mercado> findByEmail(String email);

  Optional<Mercado> findByNome(String nome);

  

}
