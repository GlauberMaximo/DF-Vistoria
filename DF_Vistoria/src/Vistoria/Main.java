package Vistoria;

import Vistoria.Controller.ClienteController;
import Vistoria.Model.Cliente;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static ClienteController controller = new ClienteController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    adicionarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientePorCpf();
                    break;
                case 4:
                    atualizarCliente();
                    break;
                case 5:
                    deletarCliente();
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- Sistema de Vistoria - Clientes ---");
        System.out.println("1. Adicionar novo cliente");
        System.out.println("2. Listar todos os clientes");
        System.out.println("3. Buscar cliente por CPF");
        System.out.println("4. Atualizar cliente");
        System.out.println("5. Deletar cliente");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void adicionarCliente() {
        System.out.println("\n--- Adicionar Novo Cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF (formato: xxx.xxx.xxx-xx): ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, cpf, telefone, email, senha);
        controller.adicionarCliente(novoCliente);
    }

    private static void listarClientes() {
        System.out.println("\n--- Lista de Todos os Clientes ---");
        List<Cliente> clientes = controller.listarTodosClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(cliente -> System.out.println(cliente.toString()));
        }
    }

    private static void buscarClientePorCpf() {
        System.out.println("\n--- Buscar Cliente por CPF ---");
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = controller.buscarClientePorCpf(cpf);
        if (cliente != null) {
            System.out.println("\nCliente Encontrado:");
            System.out.println(cliente.toString());
        } else {
            System.out.println("Cliente com o CPF " + cpf + " não encontrado.");
        }
    }

    private static void atualizarCliente() {
        System.out.println("\n--- Atualizar Cliente ---");
        System.out.print("Digite o ID do cliente que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = controller.buscarClientePorId(id);
        if (cliente == null) {
            System.out.println("Cliente com o ID " + id + " não encontrado.");
            return;
        }

        System.out.println("Cliente atual: " + cliente.toString());

        System.out.print("Novo Nome (ou pressione Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            cliente.setNome(novoNome);
        }
        
        System.out.print("Novo Telefone (ou pressione Enter para manter): ");
        String novoTelefone = scanner.nextLine();
        if (!novoTelefone.trim().isEmpty()) {
            cliente.setTelefone(novoTelefone);
        }

        System.out.print("Novo E-mail (ou pressione Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            cliente.setEmail(novoEmail);
        }
        
        System.out.print("Nova Senha (ou pressione Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.trim().isEmpty()) {
            cliente.setSenha(novaSenha);
        }

        controller.atualizarCliente(cliente);
    }

    private static void deletarCliente() {
        System.out.println("\n--- Deletar Cliente ---");
        System.out.print("Digite o ID do cliente que deseja deletar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        controller.deletarCliente(id);
    }
}