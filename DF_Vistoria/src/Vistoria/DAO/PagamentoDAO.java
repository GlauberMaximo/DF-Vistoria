package Vistoria.DAO;

import Vistoria.Conexao.ConexaoSQL;
import Vistoria.Model.FormaPagamento;
import Vistoria.Model.Pagamento;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PagamentoDAO {

    // Inserir novo pagamento
    public void inserir(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (forma_pagamento, valor, data_pagamento, idVistoria) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pagamento.getFormaPagamento().name());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setDate(3, Date.valueOf(pagamento.getDataPagamento()));
            stmt.setInt(4, pagamento.getIdVistoria());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pagamento.setIdPagamento(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pagamento: " + e.getMessage());
        }
    }

    // Buscar pagamento por ID
    public Pagamento buscarPorId(int idPagamento) {
        String sql = "SELECT * FROM pagamento WHERE idPagamento = ?";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPagamento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPagamento(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pagamento: " + e.getMessage());
        }
        return null;
    }

    // Listar todos os pagamentos
    public List<Pagamento> listarTodos() {
        List<Pagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagamento";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToPagamento(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pagamentos: " + e.getMessage());
        }
        return lista;
    }

    // Atualizar pagamento
    public void atualizar(Pagamento pagamento) {
        String sql = "UPDATE pagamento SET forma_pagamento = ?, valor = ?, data_pagamento = ?, idVistoria = ? WHERE idPagamento = ?";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pagamento.getFormaPagamento().name());
            stmt.setBigDecimal(2, pagamento.getValor());
            stmt.setDate(3, Date.valueOf(pagamento.getDataPagamento()));
            stmt.setInt(4, pagamento.getIdVistoria());
            stmt.setInt(5, pagamento.getIdPagamento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pagamento: " + e.getMessage());
        }
    }

    // Deletar pagamento
    public void deletar(int idPagamento) {
        String sql = "DELETE FROM pagamento WHERE idPagamento = ?";
        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPagamento);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar pagamento: " + e.getMessage());
        }
    }

    // Método auxiliar para mapear ResultSet -> Pagamento
    private Pagamento mapResultSetToPagamento(ResultSet rs) throws SQLException {
        int idPagamento = rs.getInt("idPagamento");
        // CORREÇÃO: Converte a string do banco de dados para maiúsculas antes de usar o valueOf
        FormaPagamento forma = FormaPagamento.valueOf(rs.getString("forma_pagamento").toUpperCase());
        BigDecimal valor = rs.getBigDecimal("valor");
        LocalDate dataPagamento = rs.getDate("data_pagamento").toLocalDate();
        int idVistoria = rs.getInt("idVistoria");

        return new Pagamento(idPagamento, forma, valor, dataPagamento, idVistoria);
    }
}