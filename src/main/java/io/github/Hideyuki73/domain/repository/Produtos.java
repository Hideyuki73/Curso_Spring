package io.github.Hideyuki73.domain.repository;

import io.github.Hideyuki73.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto,Integer> {
}
