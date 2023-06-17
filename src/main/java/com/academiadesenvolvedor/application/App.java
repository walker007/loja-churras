package com.academiadesenvolvedor.application;

import com.academiadesenvolvedor.exception.NotFoundException;
import com.academiadesenvolvedor.models.Cliente;
import com.academiadesenvolvedor.models.Endereco;
import com.academiadesenvolvedor.models.Produto;
import com.academiadesenvolvedor.persistence.HibernateUtil;
import com.academiadesenvolvedor.utils.Telefone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private Scanner scan;
    private SessionFactory sessionFactory;

    public App(Scanner scanner){
        this.scan = scanner;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void renderMenuProdutos(){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Long quantidade = (Long) session.createQuery("SELECT COUNT(p) FROM Produto p").uniqueResult();
        session.close();

        System.out.println("Produtos Cadastrados: " + quantidade.toString());
        System.out.println("1 - Cadastrar Produto");
        System.out.println("2 - Listar Produtos");
        System.out.println("3 - Consultar Produtos");
        System.out.println("4 - Apagar Produto");
        System.out.println("0 - Voltar.");
        System.out.print("Escolha uma opção: ");
    }

    public void renderMenuClientes(){
        System.out.println("Clientes Cadastrados: " + Cliente.countClientes());
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Listar Clientes");
        System.out.println("3 - Consultar Clientes");
        System.out.println("4 - Editar Clientes");
        System.out.println("5 - Consultar Cliente por ID");
        System.out.println("0 - Voltar.");
        System.out.print("Escolha uma opção: ");
    }
    public void renderMenu(){
        System.out.println("Bem-vindo à Loja de Churrasco");
        System.out.println("1 - Produtos.");
        System.out.println("2 - Clientes;");
        System.out.println("0 - Sair.");
        System.out.print("Escolha uma opção: ");
    }

    public void executar(){
        boolean sair =false;
        while (!sair){
            this.renderMenu();
            int opcao = this.scan.nextInt();
            this.scan.nextLine();

            switch (opcao){
                case 1:
                    this.executarProdutos();
                    break;
                case 2:
                    this.executarClientes();
                    break;
                case 0:
                    sair = true;
                    System.out.println("Encerrando a Lojinha... :(");
                    break;
                default :
                    System.out.println("Opção inválida...");
            }
        }
    }

    public void executarClientes(){
        boolean sair = false;
        while (!sair){
            this.renderMenuClientes();
            int opcao = this.scan.nextInt();
            this.scan.nextLine();

            switch (opcao){
                case 1:
                    this.cadastraCliente();
                    break;
                case 2:
                    this.listarClientes();
                    break;
                case 3:
                    this.consultarClientes();
                    break;
                case 4:
                    this.editarCliente();
                    break;
                case 5:
                    this.buscarCliente();
                    break;
                case 0:
                    System.out.println("Saindo do menu de clientes");
                    sair = true;
                    break;
                default :
                    System.out.println("Opção Inválida...");
            }
        }
    }

    private void buscarCliente() {
        int clienteID;
        System.out.println("Informe o ID a ser consultado");
        clienteID = this.scan.nextInt();
        this.scan.nextLine();

       try{
           Cliente cliente = Cliente.findCliente(clienteID);

           System.out.println(cliente);
       }catch (NotFoundException e){
           System.out.println(e.getMessage());
       }
    }

    public void editarCliente(){
        String cpf;
        System.out.print("Digite O CPF: ");
        cpf = this.scan.nextLine();
        Cliente cliente = null;
        List<Cliente> clientes = Cliente.searchCliente("WHERE cpf = " + cpf);

       cliente = clientes.get(0);

        if(cliente ==null){
            System.out.println("Cliente não encontrado...");
            return;
        }

        this.renderCliente(cliente);
        System.out.print("Situação true/false: ");
        boolean situacao = this.scan.nextBoolean();
        this.scan.nextLine();
        System.out.print("Crédito: ");
        double credito = this.scan.nextDouble();
        this.scan.nextLine();

        cliente.setAtivo(situacao);
        cliente.setCredito(credito);
        cliente.updateCliente();
    }
    public void consultarClientes(){
        String termo;

        System.out.print("Pesquise pelo nome: ");
        termo = this.scan.nextLine();
        Cliente cliente;
        List<Cliente> clientes = Cliente.searchCliente("WHERE nome LIKE %"+termo +"%");

        for(int i = 0;i <  clientes.size() ; i++){
            cliente = clientes.get(i);
                renderCliente(cliente);
        }

        System.out.println(clientes.size() + " Clientes foram encontrados");
    }
    public void listarClientes(){
        List<Cliente> clientes = Cliente.searchCliente("");

        for (int i = 0 ;i< clientes.size();i++){
            Cliente cliente = clientes.get(i);
            this.renderCliente(cliente);
        }
    }
    public void renderCliente(Cliente cliente){
        System.out.println(cliente);
        System.out.println("---- Endereço ---");
        System.out.println(cliente.getEndereco());
    }
    public void cadastraCliente(){
            String cpf,nome,ddd,codigoPais = "55", numero,rua,bairro,cep,cidade,uf;
            boolean ativo = true;
            double credito;

        System.out.println("Vamos Cadastrar um cliente...");
        System.out.print("Nome: ");
        nome = this.scan.nextLine();
        System.out.print("CPF (apenas números): ");
        cpf = this.scan.nextLine();
        System.out.print("DDD: ");
        ddd = this.scan.nextLine();
        System.out.print("Número: ");
        numero = this.scan.nextLine();
        System.out.println("--- Endereço ---");
        System.out.print("CEP (apenas números): ");
        cep = this.scan.nextLine();
        System.out.print("Rua: ");
        rua =  this.scan.nextLine();
        System.out.print("Bairro: ");
        bairro = this.scan.nextLine();
        System.out.print("Cidade: ");
        cidade = this.scan.nextLine();
        System.out.print("UF: ");
        uf = this.scan.nextLine();
        System.out.println("--- Configurações ---");
        System.out.print("Crédito inicial: ");
        credito = this.scan.nextDouble();
        this.scan.nextLine();

        Telefone telefone = new Telefone(codigoPais,ddd,numero);
        Endereco endereco = new Endereco(rua,bairro,cep, cidade,uf);
        Cliente cliente = new Cliente(nome, telefone, endereco, cpf, ativo, credito);
        System.out.println("Salvando no banco de dados");
        Cliente cli =  cliente.createCliente();
        endereco.createEndereco(cli.getId());
        System.out.println("Cliente Cadastrado...\n");
    }
    public void executarProdutos(){
        boolean sair = false;
        while (!sair){
            this.renderMenuProdutos();
            int opcao = this.scan.nextInt();
            this.scan.nextLine();

            switch (opcao){
                case 1:
                    this.cadastraProduto();
                    break;
                case 2:
                    this.listarProdutos();
                    break;
                case 3:
                    this.consultarProdutos();
                    break;
                case 4:
                    this.apagarProduto();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void cadastraProduto(){
        String nome;
        double preco;
        int qtd;
        Produto produto;
        Session session = this.sessionFactory.openSession();


        System.out.println("Bem-vindo(a) A tela de cadastro de produtos, forneça algumas informações para continuar...");
        System.out.print("Nome do Produto: ");
        nome = this.scan.nextLine();
        System.out.print("Preço do Produto: ");
        preco = scan.nextDouble();
        this.scan.nextLine();
        System.out.print("Quantidade: ");
        qtd = this.scan.nextInt();
        this.scan.nextLine();

        Transaction transaction = session.beginTransaction();
        try {
            produto = new Produto(nome, qtd, preco);
            session.save(produto);
            System.out.println("Produto Cadastrado com sucesso...");
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            session.close();
        }

    }

    public void listarProdutos(){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Long quantidade = (Long) session.createQuery("SELECT COUNT(p) FROM Produto p").uniqueResult();

        List<Produto> produtos = session.createQuery("FROM Produto", Produto.class).list();
        System.out.println("Listando " + quantidade.toString() + " produtos");
        Produto produto;

        for (int i = 0; i < quantidade; i++){
            produto = produtos.get(i);
            renderProduto(produto);
        }
        System.out.println("Listagem de " + quantidade.toString() + " concluida...");
        session.close();
    }

    private static void renderProduto(Produto produto) {
        System.out.println("Id: " + produto.getId());
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Preço: " + produto.getPreco());
        System.out.println("Quantidade: " + produto.getQuantidade());
        System.out.println("===========");
    }

    private void consultarProdutos(){
        System.out.println("Consulte Produtos da Loja...");
        System.out.print("Informe um nome a consultar: ");
        String termo = this.scan.nextLine();

        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Produto> produtos = session.createQuery("FROM Produto p WHERE p.nome LIKE :search")
                                        .setParameter("search","%"+termo+"%")
                                        .getResultList();
        transaction.commit();
        for (Produto produto : produtos){
            renderProduto(produto);
        }
        System.out.println("Foram encontrados "+ produtos.size() +" registros para o termo: '"+termo+"'");
        session.close();
        
    }

    private void apagarProduto(){
        System.out.print("Digite o id do Produto a ser excluído: ");
        int termo = this.scan.nextInt();
        this.scan.nextLine();
        Produto produto = null;

        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        produto = session.get(Produto.class, termo);

        if(produto != null){
          renderProduto(produto);
            System.out.println("Tem Certeza que deseja deletar este produto [s/N]");
            String confirm = this.scan.nextLine();

            if(confirm.equals("s") || confirm.equals("S")){
                session.delete(produto);
                transaction.commit();
                System.out.println("Produto Deletado...");
                session.close();
                return;
            }

            System.out.println("Abortado...");
            session.close();
           return;
        }
        session.close();
        System.out.println("Nenhum item corresponde ao termo: '"+termo+"'.");
    }
}
