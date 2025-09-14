package Vistoria.DAO;

import Vistoria.Conexao.ConexaoSQL;
import Vistoria.Model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.Year;

public class VeiculoDAO {

	public void inserir(Veiculo veiculo) {
		String sql = "INSERT INTO veiculo (placa, tipo_veiculo, nome_veiculo, modelo, ano_veiculo, chassi, observacoes, idCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getTipo_veiculo());
			stmt.setString(3, veiculo.getNome_veiculo());
			stmt.setString(4, veiculo.getModelo());
			stmt.setInt(5, veiculo.getAno_veiculo().getValue());
			stmt.setString(6, veiculo.getChassi());
			stmt.setString(7, veiculo.getObservacoes());
			stmt.setInt(8, veiculo.getIdCliente());

			stmt.executeUpdate();
			System.out.println("Veículo placa " + veiculo.getPlaca() + " inserido com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro ao inserir veículo: " + e.getMessage());
		}
	}

	public Veiculo buscarPorId(int id) {
		String sql = "SELECT * FROM veiculo WHERE idVeiculo = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Veiculo(rs.getInt("idVeiculo"), rs.getString("placa"), rs.getString("tipo_veiculo"),
							rs.getString("nome_veiculo"), rs.getString("modelo"), Year.of(rs.getInt("ano_veiculo")),
							rs.getString("chassi"), rs.getString("observacoes"), rs.getInt("idCliente"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar veículo por ID: " + e.getMessage());
		}
		return null;
	}

	public Veiculo buscarPorPlaca(String placa) {
		String sql = "SELECT * FROM veiculo WHERE placa = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, placa);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Veiculo(rs.getInt("idVeiculo"), rs.getString("placa"), rs.getString("tipo_veiculo"),
							rs.getString("nome_veiculo"), rs.getString("modelo"), Year.of(rs.getInt("ano_veiculo")),
							rs.getString("chassi"), rs.getString("observacoes"), rs.getInt("idCliente"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar veículo por placa: " + e.getMessage());
		}
		return null;
	}

	public Veiculo buscarPorChassi(String chassi) {
		String sql = "SELECT * FROM veiculo WHERE chassi = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, chassi);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Veiculo(rs.getInt("idVeiculo"), rs.getString("placa"), rs.getString("tipo_veiculo"),
							rs.getString("nome_veiculo"), rs.getString("modelo"), Year.of(rs.getInt("ano_veiculo")),
							rs.getString("chassi"), rs.getString("observacoes"), rs.getInt("idCliente"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar veículo por chassi: " + e.getMessage());
		}
		return null;
	}

	public List<Veiculo> listarTodos() {
		String sql = "SELECT * FROM veiculo";
		List<Veiculo> veiculos = new ArrayList<>();
		try (Connection conn = ConexaoSQL.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Veiculo veiculo = new Veiculo(rs.getInt("idVeiculo"), rs.getString("placa"),
						rs.getString("tipo_veiculo"), rs.getString("nome_veiculo"), rs.getString("modelo"),
						Year.of(rs.getInt("ano_veiculo")), rs.getString("chassi"), rs.getString("observacoes"),
						rs.getInt("idCliente"));
				veiculos.add(veiculo);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar veículos: " + e.getMessage());
		}
		return veiculos;
	}

	public void atualizar(Veiculo veiculo) {
		String sql = "UPDATE veiculo SET placa = ?, tipo_veiculo = ?, nome_veiculo = ?, modelo = ?, ano_veiculo = ?, chassi = ?, observacoes = ?, idCliente = ? WHERE idVeiculo = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getTipo_veiculo());
			stmt.setString(3, veiculo.getNome_veiculo());
			stmt.setString(4, veiculo.getModelo());
			stmt.setInt(5, veiculo.getAno_veiculo().getValue());
			stmt.setString(6, veiculo.getChassi());
			stmt.setString(7, veiculo.getObservacoes());
			stmt.setInt(8, veiculo.getIdCliente());
			stmt.setInt(9, veiculo.getIdVeiculo());

			stmt.executeUpdate();
			System.out.println("Veículo ID " + veiculo.getIdVeiculo() + " atualizado com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar veículo: " + e.getMessage());
		}
	}

	public void deletar(int id) {
		String sql = "DELETE FROM veiculo WHERE idVeiculo = ?";
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			int linhasAfetadas = stmt.executeUpdate();
			if (linhasAfetadas > 0) {
				System.out.println("Veículo ID " + id + " deletado com sucesso!");
			} else {
				System.out.println("Nenhum veículo encontrado com o ID " + id);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao deletar veículo: " + e.getMessage());
		}
	}

	public List<Veiculo> buscarVeiculosPorCliente(int idCliente) {
		String sql = "SELECT * FROM veiculo WHERE idCliente = ?";
		List<Veiculo> veiculos = new ArrayList<>();
		try (Connection conn = ConexaoSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idCliente);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Veiculo veiculo = new Veiculo(rs.getInt("idVeiculo"), rs.getString("placa"),
							rs.getString("tipo_veiculo"), rs.getString("nome_veiculo"), rs.getString("modelo"),
							Year.of(rs.getInt("ano_veiculo")), rs.getString("chassi"), rs.getString("observacoes"),
							rs.getInt("idCliente"));
					veiculos.add(veiculo);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar veículos do cliente: " + e.getMessage());
		}
		return veiculos;
	}
}