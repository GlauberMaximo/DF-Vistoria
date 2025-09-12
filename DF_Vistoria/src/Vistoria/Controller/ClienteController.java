package Vistoria.Controller;

import Vistoria.DAO.ClienteDAO;
import Vistoria.Model.Cliente;

import java.util.List;

public class ClienteController {
    
    private final ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    // Método de validação e inserção de um cliente
    public void adicionarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            System.out.println("Erro: O nome do cliente não pode ser vazio.");
            return;
        }
        if (cliente.getCpf() == null || !validarCpf(cliente.getCpf())) {
            System.out.println("Erro: CPF inválido.");
            return;
        }

        // Verifica se o CPF já existe antes de inserir
        if (clienteDAO.buscarPorCpf(cliente.getCpf()) != null) {
            System.out.println("Erro: Cliente com este CPF já existe.");
            return;
        }
        
        // Se as validações passarem, insere no banco
        clienteDAO.inserir(cliente);
    }

    // Método para buscar cliente por ID
    public Cliente buscarClientePorId(int id) {
        return clienteDAO.buscarPorId(id);
    }
    
    // Método para buscar cliente por CPF
    public Cliente buscarClientePorCpf(String cpf) {
        return clienteDAO.buscarPorCpf(cpf);
    }

    // Método para listar todos os clientes
    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }

    // Método para atualizar um cliente
    public void atualizarCliente(Cliente cliente) {
        // Valida se o cliente a ser atualizado existe
        if (clienteDAO.buscarPorId(cliente.getIdCliente()) == null) {
            System.out.println("Erro: Cliente não encontrado para atualização.");
            return;
        }
        clienteDAO.atualizar(cliente);
    }

    // Método para deletar um cliente
    public void deletarCliente(int id) {
        clienteDAO.deletar(id);
    }

    // Simples método de validação de CPF (você pode implementar uma lógica mais robusta)
    private boolean validarCpf(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        // Verifica se tem 11 dígitos
        return cpf.length() == 11;
    }
}