package com.br.marlon.gerenciador_pedidos.principal;

import com.br.marlon.gerenciador_pedidos.model.Categoria;
import com.br.marlon.gerenciador_pedidos.model.Produto;
import com.br.marlon.gerenciador_pedidos.repository.CategoriaRepository;
import com.br.marlon.gerenciador_pedidos.repository.PedidoRepository;
import com.br.marlon.gerenciador_pedidos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
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
            System.out.println();
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
                    break;
                case 0:
                    System.out.println("Ejetanto...");
                    break;
                default:
                    System.out.println("Opção Inválida, tente novamente.");
            }
        }
    }


    @Transactional
    public void salvarDados() {
        Scanner scanner = new Scanner(System.in);

        // Solicitar nome do produto, preço e categoria ao usuário
        System.out.println("Digite o nome do produto:");
        String nomeProduto = scanner.nextLine();


        double precoProduto = 0.0;


        boolean precoValido = false;

        // Validar entrada de preço
        while (!precoValido) {
            System.out.println("Digite o preço do produto:");
            try {
                precoProduto = scanner.nextDouble();
                precoValido = true;  // Se a entrada for válida, sai do loop
            } catch (InputMismatchException e) {
                System.out.println("Preço inválido. Por favor, insira um número válido.");
                scanner.nextLine();  // Limpar o buffer do scanner
            }
        }

        scanner.nextLine(); // Consumir a linha vazia após o nextDouble()

        System.out.println("Digite a categoria do produto (Ex: Eletrônicos, Livros):");
        String categoriaProduto = scanner.nextLine();


        List<Categoria> categorias = categoriaRepository.findAllByNome(categoriaProduto);
        Categoria categoria;
        if (categorias.isEmpty()) {
            // Se a categoria não existe, criar uma nova categoria
            categoria = new Categoria(null, categoriaProduto);
            categoriaRepository.save(categoria); // Salvar nova categoria no banco
        } else {
            categoria = categorias.get(0); // Se encontrar mais de uma, pegar a primeira
        }

        // Criar o produto com os dados fornecidos
        Produto produto = new Produto(nomeProduto, precoProduto, categoria);

        categoria.getProdutos().add(produto);

        // Salvar a categoria e o produto no banco de dados
        categoriaRepository.save(categoria);

        // Exibir confirmação e lista de categorias e seus produtos
        System.out.println("Produto cadastrado com sucesso!");


        // Voltar ao menu após a operação
        executarMenu();


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
        executarMenu();
    }

    private void listarCategoriasProdutos() {
        // Exibir as categorias e seus produtos salvos
        System.out.println("Categorias e seus produtos:");
        categoriaRepository.findAll().forEach(c -> {
            System.out.println("Categoria: " + c.getNome());
            c.getProdutos().forEach(p -> System.out.println(" - Produto: " + p.getNome() + " - Preço: " + p.getPreco()));
        });
    }
}
