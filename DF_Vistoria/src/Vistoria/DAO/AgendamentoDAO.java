package Vistoria.DAO;

import Vistoria.Conexao.ConexaoSQL;
import Vistoria.Model.Agendamento;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public void inserir(Agendamento agendamento) {
        String sql = "INSERT INTO agendamento (data_agendamento, hora, tipo_vistoria, idCliente, idVeiculo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(agendamento.getData_agendamento()));
            stmt.setTime(2, Time.valueOf(agendamento.getHora()));
            stmt.setString(3, agendamento.getTipo_vistoria());
            stmt.setInt(4, agendamento.getIdCliente());
            stmt.setInt(5, agendamento.getIdVeiculo());

            stmt.executeUpdate();
            System.out.println("Agendamento inserido com sucesso para o cliente ID " + agendamento.getIdCliente());
        } catch (SQLException e) {
            System.err.println("Erro ao inserir agendamento: " + e.getMessage());
        }
    }

    public Agendamento buscarPorId(int id) {
        String sql = "SELECT * FROM agendamento WHERE idAgendamento = ?";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Agendamento(
                        rs.getInt("idAgendamento"),
                        rs.getDate("data_agendamento").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        rs.getString("tipo_vistoria"),
                        rs.getString("status_agendamento"),
                        rs.getInt("idCliente"),
                        rs.getInt("idVeiculo")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar agendamento por ID: " + e.getMessage());
        }
        return null;
    }
    
    public List<Agendamento> listarTodos() {
        String sql = "SELECT * FROM agendamento";
        List<Agendamento> agendamentos = new ArrayList<>();
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Agendamento agendamento = new Agendamento(
                    rs.getInt("idAgendamento"),
                    rs.getDate("data_agendamento").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("tipo_vistoria"),
                    rs.getString("status_agendamento"),
                    rs.getInt("idCliente"),
                    rs.getInt("idVeiculo")
                );
                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar agendamentos: " + e.getMessage());
        }
        return agendamentos;
    }
    
    public void atualizar(Agendamento agendamento) {
        String sql = "UPDATE agendamento SET data_agendamento = ?, hora = ?, tipo_vistoria = ?, status_agendamento = ?, idCliente = ?, idVeiculo = ? WHERE idAgendamento = ?";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(agendamento.getData_agendamento()));
            stmt.setTime(2, Time.valueOf(agendamento.getHora()));
            stmt.setString(3, agendamento.getTipo_vistoria());
            stmt.setString(4, agendamento.getStatus_agendamento());
            stmt.setInt(5, agendamento.getIdCliente());
            stmt.setInt(6, agendamento.getIdVeiculo());
            stmt.setInt(7, agendamento.getIdAgendamento());

            stmt.executeUpdate();
            System.out.println("Agendamento ID " + agendamento.getIdAgendamento() + " atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar agendamento: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM agendamento WHERE idAgendamento = ?";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Agendamento ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum agendamento encontrado com o ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar agendamento: " + e.getMessage());
        }
    }
    
    public List<Agendamento> buscarAgendamentosPorCliente(int idCliente) {
        String sql = "SELECT * FROM agendamento WHERE idCliente = ?";
        List<Agendamento> agendamentos = new ArrayList<>();
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento agendamento = new Agendamento(
                        rs.getInt("idAgendamento"),
                        rs.getDate("data_agendamento").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        rs.getString("tipo_vistoria"),
                        rs.getString("status_agendamento"),
                        rs.getInt("idCliente"),
                        rs.getInt("idVeiculo")
                    );
                    agendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar agendamentos do cliente: " + e.getMessage());
        }
        return agendamentos;
    }
}