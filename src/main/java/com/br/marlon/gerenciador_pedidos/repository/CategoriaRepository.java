package com.br.marlon.gerenciador_pedidos.repository;

import com.br.marlon.gerenciador_pedidos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
