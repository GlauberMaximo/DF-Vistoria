package Vistoria.DAO;

import Vistoria.Model.Desligamento;
import Vistoria.Conexao.ConexaoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DesligamentoDAO {

    /**
     * Registra um novo desligamento no banco de dados
     */
    public void registrar(Desligamento desligamento) {
        String sql = "INSERT INTO desligamento_funcionario (id_funcionario, nome_funcionario, motivo) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, desligamento.getIdFuncionario());
            stmt.setString(2, desligamento.getNomeFuncionario());
            stmt.setString(3, desligamento.getMotivo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao registrar desligamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lista todos os desligamentos ordenados pela data (mais recente primeiro)
     */
    public List<Desligamento> listarTodos() {
        List<Desligamento> desligamentos = new ArrayList<>();
        String sql = "SELECT * FROM desligamento_funcionario ORDER BY data_desligamento DESC";

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Desligamento d = new Desligamento();
                d.setId(rs.getInt("id"));
                d.setIdFuncionario(rs.getInt("id_funcionario"));
                d.setNomeFuncionario(rs.getString("nome_funcionario"));
                d.setMotivo(rs.getString("motivo"));
                d.setDataDesligamento(rs.getTimestamp("data_desligamento").toLocalDateTime());

                desligamentos.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar desligamentos: " + e.getMessage());
            e.printStackTrace();
        }

        return desligamentos;
    }
}
