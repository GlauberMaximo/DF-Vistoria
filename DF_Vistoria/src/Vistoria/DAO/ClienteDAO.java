package Vistoria.DAO;

import Vistoria.Conexao.ConexaoSQL;
import Vistoria.Model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Método para inserir um novo cliente no banco de dados
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, email, senha) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenha());
            
            stmt.executeUpdate();
            System.out.println("Cliente " + cliente.getNome() + " inserido com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar o cliente
    }
    
    // Método para buscar um cliente pelo CPF
    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
        }
        return null;
    }

    // Método para listar todos os clientes
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // Método para atualizar os dados de um cliente
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ?, senha = ? WHERE idCliente = ?";
        
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenha());
            stmt.setInt(6, cliente.getIdCliente());
            
            stmt.executeUpdate();
            System.out.println("Cliente ID " + cliente.getIdCliente() + " atualizado com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // Método para deletar um cliente pelo ID
    public void deletar(int id) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Cliente ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }
}