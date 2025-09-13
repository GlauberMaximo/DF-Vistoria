package Vistoria.DAO;

import Vistoria.Conexao.ConexaoSQL;
import Vistoria.Model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

	public void inserir(Funcionario funcionario) {
		String sql = "INSERT INTO funcionario (nome, email, matricula, cargo, senha) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getEmail());
			stmt.setString(3, funcionario.getMatricula());
			stmt.setString(4, funcionario.getCargo());
			stmt.setString(5, funcionario.getSenha());
			stmt.executeUpdate();
			System.out.println("Funcionário " + funcionario.getNome() + " inserido com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro ao inserir funcionário: " + e.getMessage());
		}
	}

	public Funcionario buscarPorId(int id) {
		String sql = "SELECT * FROM funcionario WHERE idFuncionario = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Funcionario(rs.getInt("idFuncionario"), rs.getString("nome"), rs.getString("email"),
							rs.getString("matricula"), rs.getString("cargo"), rs.getString("senha"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
		}
		return null;
	}

	public Funcionario buscarPorMatricula(String matricula) {
		String sql = "SELECT * FROM funcionario WHERE matricula = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, matricula);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Funcionario(rs.getInt("idFuncionario"), rs.getString("nome"), rs.getString("email"),
							rs.getString("matricula"), rs.getString("cargo"), rs.getString("senha"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar funcionário por matrícula: " + e.getMessage());
		}
		return null;
	}

	public List<Funcionario> listarTodos() {
		String sql = "SELECT * FROM funcionario";
		List<Funcionario> funcionarios = new ArrayList<>();
		try (Connection conn = ConexaoSQL.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Funcionario funcionario = new Funcionario(rs.getInt("idFuncionario"), rs.getString("nome"),
						rs.getString("email"), rs.getString("matricula"), rs.getString("cargo"), rs.getString("senha"));
				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar funcionários: " + e.getMessage());
		}
		return funcionarios;
	}

	public void atualizar(Funcionario funcionario) {
		String sql = "UPDATE funcionario SET nome = ?, email = ?, matricula = ?, cargo = ?, senha = ? WHERE idFuncionario = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getEmail());
			stmt.setString(3, funcionario.getMatricula());
			stmt.setString(4, funcionario.getCargo());
			stmt.setString(5, funcionario.getSenha());
			stmt.setInt(6, funcionario.getIdFuncionario());
			stmt.executeUpdate();
			System.out.println("Funcionário ID " + funcionario.getIdFuncionario() + " atualizado com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
		}
	}

	public void deletar(int id) {
		String sql = "DELETE FROM funcionario WHERE idFuncionario = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			int linhasAfetadas = stmt.executeUpdate();
			if (linhasAfetadas > 0) {
				System.out.println("Funcionário ID " + id + " deletado com sucesso!");
			} else {
				System.out.println("Nenhum funcionário encontrado com o ID " + id);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao deletar funcionário: " + e.getMessage());
		}
	}
}