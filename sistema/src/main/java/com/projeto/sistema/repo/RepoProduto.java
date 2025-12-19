package com.projeto.sistema.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.projeto.sistema.model.Produto;


@Repository
public interface RepoProduto extends CrudRepository<Produto, Long> {

  Optional<Iterable<Produto>> findByMercadoId(Long mercadoId);

  List<Produto> findByQuantidadeLessThanEqual(Integer quantidade);



}
