package com.br.marlom.gerenciador_pedidos.repository;

import com.br.marlom.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
