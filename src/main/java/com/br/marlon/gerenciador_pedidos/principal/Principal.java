package com.br.marlon.gerenciador_pedidos.principal;

import com.br.marlon.gerenciador_pedidos.model.Categoria;
import com.br.marlon.gerenciador_pedidos.model.Produto;
import com.br.marlon.gerenciador_pedidos.repository.CategoriaRepository;
import com.br.marlon.gerenciador_pedidos.repository.PedidoRepository;
import com.br.marlon.gerenciador_pedidos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    private Scanner leitura = new Scanner(System.in);

    public void executarMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1-Cadastrar Produtos
                    2-Listar Produtos Cadastrados no Banco de Dados
                    
                    0 - Sair
                    """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
               case 1:
                    salvarDados();
                    break;
                case 2:
                    listarProdutos();
                case 0:
                    System.out.println("Ejetanto...");
                    break;
                default:
                    System.out.println("Opção Inválida, tente novamente.");
            }
        }
    }


    @Transactional
    public void salvarDados()

    {
        Categoria categoriaEletronicos = new Categoria(null, "Eletrônicos");
        Categoria categoriaLivros = new Categoria(null, "Livros");

        Produto produto = new Produto("Notebook Dell II", 2500.00, categoriaEletronicos);
        Produto produto1 = new Produto("Livro JAVAI", 99.00, categoriaLivros);

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

    private void listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados.");
        } else {
            System.out.println("Lista de Produtos:");
            for (Produto produto : produtos) {
                System.out.println("Nome: " + produto.getNome() + ", Preço: " + produto.getPreco() + ", Categoria: " + produto.getCategoria().getNome());
            }
        }
    }
}