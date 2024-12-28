package com.br.marlon.gerenciador_pedidos.repository;

import com.br.marlon.gerenciador_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
