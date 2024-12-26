package com.br.marlom.gerenciador_pedidos.repository;

import com.br.marlom.gerenciador_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
