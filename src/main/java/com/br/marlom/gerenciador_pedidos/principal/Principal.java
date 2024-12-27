package com.br.marlom.gerenciador_pedidos.principal;

import com.br.marlom.gerenciador_pedidos.model.Categoria;
import com.br.marlom.gerenciador_pedidos.model.Pedido;
import com.br.marlom.gerenciador_pedidos.model.Produto;
import com.br.marlom.gerenciador_pedidos.repository.CategoriaRepository;
import com.br.marlom.gerenciador_pedidos.repository.PedidoRepository;
import com.br.marlom.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Principal {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    public void salvarDados()

    {
        Categoria categoriaEletronicos = new Categoria(null, "Eletrônicos");
        Categoria categoriaLivros = new Categoria(null, "Livros");

        Produto produto = new Produto("Notebook Dell", 2990.00, categoriaEletronicos);
        Produto produto1 = new Produto("Livro Javas", 100.00, categoriaLivros);

        categoriaLivros.setProdutos(List.of(produto1));
        categoriaEletronicos.setProdutos(List.of(produto));

        categoriaRepository.saveAll(List.of(categoriaEletronicos, categoriaLivros));

        System.out.println("Categorias e seus produtos:");
        categoriaRepository.findAll().forEach(categoria -> {
            System.out.println("Categoria: " + categoria.getNome());
            categoria.getProdutos().forEach(p ->
                    System.out.println(" - Produto: " + produto.getNome())
            );
        });
    }
}
